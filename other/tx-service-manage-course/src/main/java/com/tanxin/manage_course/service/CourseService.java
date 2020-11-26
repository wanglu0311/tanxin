package com.tanxin.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.cms.response.CmsPostPageResult;
import com.tanxin.framework.domain.course.*;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import com.tanxin.framework.domain.course.ext.CourseView;
import com.tanxin.framework.domain.course.ext.TeachplanNode;
import com.tanxin.framework.domain.course.request.CourseListRequest;
import com.tanxin.framework.domain.course.response.CourseCode;
import com.tanxin.framework.domain.course.response.CoursePublishResult;
import com.tanxin.framework.exception.ExceptionCast;
import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.manage_course.client.CmsPageClient;
import com.tanxin.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CourseService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/16 20:21
 */
@Service
public class CourseService {

    @Resource
    private TeachPlanMapper teachPlanMapper;

    @Autowired
    private TeachplanRepository teachplanRepository;

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseMarketRepository courseMarketRepository;

    @Resource
    private CoursePicRepository coursePicRepository;

    @Autowired
    private CmsPageClient cmsPageClient;

    @Autowired
    private CousrsePubRepository cousrsePubRepository;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;

    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;

    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;

    @Value("${course-publish.siteId}")
    private String publish_siteId;

    @Value("${course-publish.templateId}")
    private String publish_templateId;

    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    //TODO 课程信息查询
    public QueryResponseResult findCourseInfo(Integer page, Integer size, CourseListRequest courseListRequest) {

        // 开始分页
        PageHelper.startPage(page, size);

        // 执行查询方法
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);

        // QueryResult对象
        QueryResult<CourseInfo> result = new QueryResult<>();
        result.setList(courseListPage.getResult());
        result.setTotal(courseListPage.getTotal());

        // 返回信息
        return new QueryResponseResult(CommonCode.SUCCESS, result);
    }

    //TODO 课程计划查询
    public TeachplanNode findTeachplanList(String courseId) {
        return teachPlanMapper.selectList(courseId);
    }

    //TODO 添加课程计划
    public ResponseResult addTeachplan(Teachplan teachplan) {

        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {

            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 判断时长是否是否正确
        Double timelength = teachplan.getTimelength();

        // 当时长不为空且大于999分钟或者小于0时抛出异常
        if (timelength != null && (timelength > 999.99 || timelength < 0)) {

            ExceptionCast.cast(CommonCode.ERROR_TIME);
        }

        // 取出课程id
        String courseid = teachplan.getCourseid();

        // parentId
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)) {

            // 取出该课程的根节点
            parentid = getTeachplanRoot(courseid);
//            System.out.println("parentid = " + parentid);
        }

        // 新节点
        Teachplan teachplan1 = new Teachplan();

        // 将页面定义的信息拷贝到teachplan1对象
        BeanUtils.copyProperties(teachplan, teachplan1);

        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        Teachplan teachplan2 = optional.get();
        String grade = teachplan2.getGrade();

        // 设置信息
        teachplan1.setParentid(parentid);
        teachplan1.setCourseid(courseid);

        //根据父节点的级别来设置
        if (grade.equals("1")) {
            teachplan1.setGrade("2");
        } else {
            teachplan1.setGrade("3");
        }

        teachplanRepository.save(teachplan1);

        return new ResponseResult(CommonCode.SUCCESS);
    }


    //TODO 添加课程
    public ResponseResult addCourse(CourseBase courseBase) {

        CourseBase save = courseBaseRepository.save(courseBase);

        return ResponseResult.SUCCESS();
    }

    //TODO 根据课程id查询
    public CourseInfo findCourse(String courseId) {

        // 判断参数是否存在
        if (StringUtils.isEmpty(courseId)) {

            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        return courseMapper.findCourse(courseId);
    }

    public ResponseResult updateCoursebase(String courseId, CourseBase courseBase) {

        // 判断课程id是否为空
        if (StringUtils.isEmpty(courseId)) {

            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 是否存在
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (optional.isPresent()) {

            // 进行修改
            courseBaseRepository.save(courseBase);
            return ResponseResult.SUCCESS();
        }

        return ResponseResult.FAIL();
    }

    //TODO 根据课程id查询课程营销信息
    public CourseMarket getCourseMarketById(String courseId) {

        // 判断课程id是否为空
        if (StringUtils.isEmpty(courseId)) {

            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 进行查询
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);

        // 如果为空则返回null
        return optional.orElse(null);

    }


    //TODO 根据课程id修改课程营销信息
    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket) {

        // 是否存在
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if (optional.isPresent()) {

            // 指定开始 和 指定结束时间的验证
            Date start = courseMarket.getStartTime();
            Date end = courseMarket.getEndTime();
            // 获取当前时间
            Date now = new Date();

            // 起始时间在当前时间之后  结束时间在起始时间之后
            if (start.before(now) || end.before(start)) {

                ExceptionCast.cast(CommonCode.INVALID_PARAM);
            }

            // 进行修改
            courseMarketRepository.save(courseMarket);
            return ResponseResult.SUCCESS();
        }

        courseMarket.setExpires(new Date());
        // 进行添加
        courseMarketRepository.save(courseMarket);
        return ResponseResult.SUCCESS();

    }



    //TODO 向课程管理添加课程相关的信息
    public ResponseResult addCoursePic(String courseId, String pic) {

        // 判断是否为空
        if (StringUtils.isEmpty(courseId) || StringUtils.isEmpty(pic)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 判断是否已经存在
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        if (optional.isPresent()) {

            CoursePic coursePic = optional.get();
            coursePic.setPic(pic);

            // 进行修改
            coursePicRepository.save(new CoursePic(courseId, pic));

            return new ResponseResult(CommonCode.SUCCESS);
        }


        // 进行添加
        coursePicRepository.save(new CoursePic(courseId, pic));

        // 返回状态
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //TODO 查询课程图片
    public CoursePic findCoursePic(String courseId) {

        // 判断id是否为空
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        // 查询图片
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);

        return optional.orElse(null);

    }

    //TODO 删除课程图片
    public ResponseResult deleteCouresPic(String courseId) {

        // 判断id是否为空
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        long result = coursePicRepository.deleteByCourseid(courseId);
        if (result > 0) {
            return ResponseResult.SUCCESS();
        }

        return ResponseResult.FAIL();

    }


    //TODO 查询课程视图,包括基本信息 图片 营销信息 课程计划
    public CourseView getCourseView(String id) {

        CourseView courseView = new CourseView();

        // 课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        courseBaseOptional.ifPresent(courseView::setCourseBase);

        // 课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        courseMarketOptional.ifPresent(courseView::setCourseMarket);

        // 课程图片
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(id);
        coursePicOptional.ifPresent(courseView::setCoursePic);

        // 课程计划信息
        TeachplanNode teachplanNode = teachPlanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);

        return courseView;

    }

    //TODO 课程预览
    public CoursePublishResult preview(String id) {

        // 请求cms添加页面
        CmsPage cmsPage = baseToCmsPage(id);

        // 远程调用cms
        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if (!cmsPageResult.isSuccess()) {

            // 返回失败
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        CmsPage cmsPage1 = cmsPageResult.getCmsPage();
        String pageId = cmsPage1.getPageId();
        // 拼装页面预览的url
        String url = previewUrl + pageId;

        // 返回CoursePublishResult 对象
        return new CoursePublishResult(CommonCode.SUCCESS, url);
    }


    //TODO 最新课程
    public String latest() {

        // 得到最新课程营销id
        CourseMarket courseMarket = courseMarketRepository.findByExpiresdesc();
        String id = courseMarket.getId();

        // 得到课程信息
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        CourseBase courseBase = null;
        if (optional.isPresent()) {
            courseBase=optional.get();
        }

        // 拼接返回信息
        return "最新课程名称:" + courseBase.getName() + "\t价格仅有" + courseMarket.getPrice();
    }

    //TODO 发布课程
    public CoursePublishResult publish(String id) {

        // 根据id查询信息
        CmsPage cmsPage = baseToCmsPage(id);

        // 调用cms一键发布接口将课程详情页面发布到服务器
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if (!cmsPostPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        // 保存课程的发布状态为"已发布"
        CourseBase courseBase = saveCoursePubStatus(id);
        if (courseBase == null) {
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        // 保存课程索引信息
        // 创建 CoursePub对象
        CoursePub coursePub = createCoursePub(id);

        // 保存到数据库
        saveCoursePub(id, coursePub);

        // 缓存课程的信息

        // 得到页面的url
        String pageUrl = cmsPostPageResult.getPageUrl();

        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    //TODO 将coursePub保存到数据库
    private CoursePub saveCoursePub(String id,CoursePub coursePub) {

        CoursePub coursePubNew = null;

        // 根据id查询coursePub
        Optional<CoursePub> pubOptional = cousrsePubRepository.findById(id);
        coursePubNew = pubOptional.orElse(new CoursePub());

        // 将coursePub对象中的信息保存到coursepub中
        BeanUtils.copyProperties(coursePub,coursePubNew);
        coursePubNew.setId(id);

        // 发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePubNew.setPubTime(date);
        cousrsePubRepository.save(coursePubNew);
        return coursePubNew;
    }

    //TODO 创建CoursePub对象
    private CoursePub createCoursePub(String id) {

        CoursePub coursePub = new CoursePub();
        // 根据课程 id 查询course_base
        Optional<CourseBase> optionalBase = courseBaseRepository.findById(id);
        if (optionalBase.isPresent()) {
            CourseBase courseBase = optionalBase.get();
            // 属性拷贝到cursePub中
            BeanUtils.copyProperties(courseBase, coursePub);
        }


        // 根据课程 id 查询course_pic
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if (picOptional.isPresent()) {
            CoursePic coursePic = picOptional.get();
            // 属性拷贝到cursePub中
            BeanUtils.copyProperties(coursePic, coursePub);
        }

        // 根据课程 id 查询营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(id);
        if (marketOptional.isPresent()) {
            CourseMarket courseMarket = marketOptional.get();
            // 属性拷贝到cursePub中
            BeanUtils.copyProperties(courseMarket, coursePub);
        }

        // 根据课程 id 查询课程计划信息
        TeachplanNode teachplanNode = teachPlanMapper.selectList(id);
        String jsonString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(jsonString);

        return coursePub;
    }

    //TODO 更改课程状态 为已发布 202002
    private CourseBase saveCoursePubStatus(String courseId) {

        CourseBase courseById = this.findCourseById(courseId);
        courseById.setStatus("202002");
        courseBaseRepository.save(courseById);
        return courseById;
    }

    //TODO 得到课程计划的根
    private String getTeachplanRoot(String courseid) {

        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if (!optional.isPresent()) {
            return null;
        }

        // 课程信息
        CourseBase courseBase = optional.get();

        // 查询课程的根节点
        List<Teachplan> teachplanList = teachplanRepository.findByParentidAndCourseid("0", courseid);
        if (teachplanList == null || teachplanList.size() <= 0) {

            // 查询不到,自动添加
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setPname(courseBase.getName());
            teachplan.setCourseid(courseid);
            teachplan.setStatus("0");

            // 添加并返回id
            return teachplanRepository.save(teachplan).getId();

        }

//        System.out.println("teachplanList.get(0).getId() = " + teachplanList.get(0).getId());
        // 返回根节点
        return teachplanList.get(0).getId();
    }

    //TODO 根据id查询课程信息并转成cmsPage
    private CmsPage baseToCmsPage(String id) {
        CourseBase courseById = this.findCourseById(id);
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setDataUrl(publish_dataUrlPre + id);
        cmsPage.setPageName(id + ".html");
        cmsPage.setPageAliase(courseById.getName());
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setTemplateId(publish_templateId);
        return cmsPage;
    }


    //TODO 根据id查询课程基本信息
    private CourseBase findCourseById(String courseId) {

        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (optional.isPresent()) {
            return optional.get();
        }
        ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        return null;

    }
}
