package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
import com.example.springbootgraduationdesign.component.RequestComponent;
import com.example.springbootgraduationdesign.component.vo.*;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.service.*;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/student/")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private MatchService matchService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CheckIsNullComponent checkIsNullComponent;
    @Autowired
    private RequestComponent requestComponent;


    @GetMapping("index")
    public Map getIndex(){
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        List<String> positionsName = positionService.listPositionsName();
        List<String> industriesName = industryService.listIndustriesName();
        List<String> studentPositionsName = positionService.listPositionNameByStudent(sid);
        List<String> studentIndustryName = industryService.listIndustryNameByStudent(sid);
        List<String> professionsSClass = professionService.getProfessionsSName();
        return Map.of(
                "student",student,
                "positionsName",positionsName,
                "studentPositionsName",studentPositionsName,
                "studentIndustryName",studentIndustryName,
                "industriesName",industriesName,
                "professionsSClass",professionsSClass
        );
    }

    @PatchMapping("information")
    public Map updateStudentInformation(@RequestBody StudentVo studentVo){
        int sid = requestComponent.getUid();
        Student student = studentVo.getStudent();
        student.setS_id(sid);
        Student studentOld = studentService.getStudent(sid);
        if (studentOld == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "?????????????????????");
        }
        Profession profession = professionService.getProfessionBySClass(student.getS_profession().getPr_s_class());
        if (profession == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "???????????????????????????");
        }
        student.setS_profession(profession);
        if (student.getS_if_work().equals(EnumWarehouse.IF_IS_OR_NOT.???)){
            if (student.getS_w_province() == null || student.getS_company() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "?????????????????????????????????????????????");
            }
        }else {
            student.setS_w_province("???");
            student.setS_company("???");
        }
        List<Position> positions = positionService.getPositionsByPositionsName(studentVo.getPositionsName());
        if (positions.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "?????????????????????????????????");
        }
        List<Industry> industries = industryService.getIndustriesByIndustriesName(studentVo.getIndustriesName());
        if (industries.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "?????????????????????????????????");
        }

        student.setS_telephone(studentOld.getS_telephone());
        student.setS_password(studentOld.getS_password());
        LocalDateTime localDateTime = LocalDateTime.now();
        student.setInsertTime(localDateTime);
        student.setUpdateTime(localDateTime);
        if (checkIsNullComponent.objCheckIsNull(student)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        studentVo.setPositions(positions);
        studentVo.setIndustries(industries);
        studentService.updateStudent(studentVo);
        return Map.of(
                "student",student
        );
    }

    @PatchMapping("password")
    public Map updatePassword(@RequestBody PasswordVo passwordVo){
        Student s = studentService.getStudent(requestComponent.getUid());
        if (!encoder.matches(passwordVo.getOldPassword(), s.getS_password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "????????????????????????");
        }else {
            s.setS_password(encoder.encode(passwordVo.getNewPassword()));
        }
        studentService.updateStudent(s);
        return Map.of(
                "student",s
        );
    }

    @PostMapping("resume")
    public Map addResume(@RequestBody Resume resume){
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        resume.setR_student(student);
        LocalDateTime localDateTime = LocalDateTime.now();
        resume.setInsertTime(localDateTime);
        resume.setUpdateTime(localDateTime);
        if (checkIsNullComponent.objCheckIsNull(resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "?????????????????????????????????????????????????????????");
        }
        studentService.addResume(resume);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @DeleteMapping("resume/{rid}")
    public Map deleteResume(@PathVariable int rid){
        Resume resume = studentService.getResume(rid);
        if (resume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        studentService.deleteResumeAndRelated(resume);

        List<Resume> resumes = studentService.getResumesByStudentId(requestComponent.getUid());
        return Map.of(
                "resumes",resumes
        );
    }

    @PatchMapping("resume")
    public Map updateResume(@RequestBody Resume resume){
        int sid = requestComponent.getUid();
        Student s = studentService.getStudent(sid);
        resume.setR_student(s);
        LocalDateTime localDateTime = LocalDateTime.now();
        resume.setInsertTime(localDateTime);
        resume.setUpdateTime(localDateTime);
        if (checkIsNullComponent.objCheckIsNull(resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "?????????????????????????????????????????????????????????");
        }
        if (studentService.getResume(resume.getR_id()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        studentService.updateResume(resume);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @GetMapping("resumes")
    public Map getResumes(){
        List<Resume> resumes = studentService.getResumesByStudentId(requestComponent.getUid());
        return Map.of(
                "resumes",resumes
        );
    }


    @PostMapping("studentResume")
    public Map addStudentResume(@RequestBody Resume resume){
        int rid = resume.getR_id();
        Resume r = studentService.getResume(rid);
        if (r == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        studentService.addStudentResume(student,r);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @DeleteMapping("studentResume/{rid}")
    public Map deleteStudentResume(@PathVariable int rid){
        StudentResume studentResume = studentService.getStudentResumeByResume(rid);
        if (studentResume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        studentService.deleteStudentResumeAndRelatedByResume(rid);
        Resume resume = studentService.getResume(rid);
        resume.setPosted(false);
        studentService.updateResume(resume);
        int sid = requestComponent.getUid();
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );

    }


    @PostMapping("jmr/{rid}")
    public Map getJmr(@PathVariable int rid, @RequestBody PersonalizedJMRVo personalizedJMRVo){
        PersonalizedJMRVo pJMRVo = matchService.transferPersonalizedJMRVoWeight(personalizedJMRVo);
        List<ResumeJMRPersonalizedVo> resumeJMRsPersonalized = studentService.getResumeJMR_Match(rid,pJMRVo);
        return Map.of(
                "resumeJMRsPersonalized", resumeJMRsPersonalized
        );
    }

    @GetMapping("jmr/{rid}")
    public Map getSmr(@PathVariable int rid){
        System.out.println("getSmr rid:" + rid);
        StudentResume studentResume = studentService.getStudentResumeByResume(rid);
        if (studentResume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "?????????????????????????????????????????????");
        }
        List<ResumeJMR> resumeJMRs = studentService.getResumeJMR_Match(rid);
        return Map.of(
                "resumeJMRs", resumeJMRs
        );
    }


    @PostMapping("studentFavoredJob/{jid}")
    public Map addStudentFavoredJob(@PathVariable int jid){
        Job j = companyService.getJob(jid);
        if (j == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        Student student = studentService.getStudent(requestComponent.getUid());
        StudentFavoredJob studentFavoredJob = new StudentFavoredJob();
        StudentFavoredJobPK studentFavoredJobPK = new StudentFavoredJobPK();
        studentFavoredJobPK.setSfj_student(student);
        studentFavoredJobPK.setSfj_job(j);
        studentFavoredJob.setStudentFavoredJobPK(studentFavoredJobPK);
        studentService.addStudentFavoredJob(studentFavoredJob);
        List<StudentFavoredJob> studentFavoredJobs = studentService.getStudentFavoredJobsByStudent(requestComponent.getUid());
        return Map.of(
                "studentFavoredJobs",studentFavoredJobs
        );
    }

    @GetMapping("studentFavoredJob")
    public Map getStudentFavoredJobs(){
        List<StudentFavoredJob> studentFavoredJobs = studentService.getStudentFavoredJobsByStudent(requestComponent.getUid());
        return Map.of(
                "studentFavoredJobs",studentFavoredJobs
        );
    }

    @DeleteMapping("studentFavoredJob/{jid}")
    public Map deleteStudentFavoredJob(@PathVariable int jid){
        int sid = requestComponent.getUid();
        StudentFavoredJob studentFavoredJob = studentService.getStudentFavoredJobByStudentAndJob(sid,jid);
        if (studentFavoredJob == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "????????????????????????????????????");
        }
        studentService.deleteStudentFavoredJobByStudentAndJob(sid, jid);
        List<StudentFavoredJob> studentFavoredJobs = studentService.getStudentFavoredJobsByStudent(requestComponent.getUid());
        return Map.of(
                "studentFavoredJobs",studentFavoredJobs
        );
    }

    @GetMapping("studentJobs")
    public Map getStudentJobs(){
        int sid = requestComponent.getUid();
        List<JobResume> jobResumes = studentService.getJobResumsByStudent(sid);
        return Map.of(
                "jobResumes",jobResumes
        );
    }

    @PostMapping("studentJobs/jobResume/{jid}/{rid}")
    public Map addJobResume(@PathVariable int rid, @PathVariable int jid){
        int sid = requestComponent.getUid();
        Job job = companyService.getJob(jid);
        Resume resume = studentService.getResume(rid);
        if (job == null || resume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????????????????");
        }
        JobResume jobResume = studentService.getJobResumeByJobAndResume(jid,rid );
        if (jobResume == null){
            jobResume = new JobResume();
            JobResumePK jobResumePK = new JobResumePK();
            jobResume.setJobToResume(false);
            jobResume.setResumeToJob(true);
            jobResumePK.setJr_resume(resume);
            jobResumePK.setJr_job(job);
            jobResume.setJobResumePK(jobResumePK);
            studentService.addJobResume(jobResume);
        }else {
            jobResume.setResumeToJob(true);
            studentService.updateJobResume(jobResume);
        }
        List<JobResume> jobResumes = studentService.getJobResumsByStudent(sid);
        return Map.of(
                "jobResumes",jobResumes
        );
    }

    @PostMapping("studentJobs/jobResume/{jid}")
    public Map addJobResume(@PathVariable int jid){
        int sid = requestComponent.getUid();
        Job job = companyService.getJob(jid);
        if (job == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "?????????????????????????????????");
        }
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        if (resumes.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "????????????????????????????????????????????????");
        }
        Resume resume = resumes.get(0);
        JobResume jobResume = studentService.getJobResumeByJobAndResume(jid, resume.getR_id());
        if (jobResume == null){
            jobResume = new JobResume();
            JobResumePK jobResumePK = new JobResumePK();
            jobResume.setJobToResume(false);
            jobResume.setResumeToJob(true);
            jobResumePK.setJr_resume(resume);
            jobResumePK.setJr_job(job);
            jobResume.setJobResumePK(jobResumePK);
            studentService.addJobResume(jobResume);
        }else {
            jobResume.setResumeToJob(true);
            studentService.updateJobResume(jobResume);
        }
        List<JobResume> jobResumes = studentService.getJobResumsByStudent(sid);
        return Map.of(
                "jobResumes",jobResumes
        );
    }

    @GetMapping("jobRecommend")
    public Map getJobRecommends(){
        int sid = requestComponent.getUid();
        List<JobRecommendVo> jobRecommendVos = recommendService.getJobRecommends(sid);
        return Map.of(
                "jobRecommendVos",jobRecommendVos
        );
    }

}
