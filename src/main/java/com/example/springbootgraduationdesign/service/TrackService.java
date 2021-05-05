package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.TransferComponent;
import com.example.springbootgraduationdesign.component.ValueComponent;
import com.example.springbootgraduationdesign.component.vo.CompanyListStatisticalFormVo;
import com.example.springbootgraduationdesign.component.vo.JobListStatisticalFormVo;
import com.example.springbootgraduationdesign.component.vo.ResumeListStatisticalFormVo;
import com.example.springbootgraduationdesign.component.vo.StudentListStatisticalFormVo;
import com.example.springbootgraduationdesign.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
public class TrackService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private TransferComponent transferComponent;
    @Autowired
    private TrackService trackService;
    @Autowired
    private ValueComponent valueComponent;

    /*----------------倾向跟踪：计算系统默认权重--------------------
    ----------------------------------------------------------*/

    //获得为企业端服务的系统默认权重 计算ResumeSystemDefaultWeight
    public List<Double> getJobSMRSystemDefaultWeight(){
        List<Double> weights = new ArrayList<>();
        List<Resume> resumes = studentService.getAllFavoredResumes();
        int n = resumes.size();
        if (n != 0){
            List<List<Double>> lists = new ArrayList<>();//存最终结果，一个list<Double>存入一个特征的所有值
            List<double[]> listArr = new ArrayList<>();//存数据，一个double[]存入一个学生简历的对应数据
            List<double[]> listArrTempt = new ArrayList<>();//临时翻转数据，将学生简历的对应数据存在一个arr翻转为一个特征的所有值存在一个list
            for (Resume resume : resumes) {
                double[] arr = transferComponent.transferResumeAndStudentToArray(resume);
                listArr.add(arr);
            }
            int m = listArr.get(0).length;//一共有多少个特征，也是lists的长度，也就是有几个List<Double>。
            //初始化lists\listArrTempt
            for (int j = 0; j < m; j++){
                double[] arrTempt = new double[n];
                listArrTempt.add(arrTempt);
            }
            //将listArr数据存入listArrTempt
            int i = 0;
            for (double[] arr : listArr){
                for (int j = 0; j < m; j++){
                    listArrTempt.get(j)[i] = arr[j];
                }
                i++;
            }
            //将listArrTempt数据翻转到lists
            for (double[] arr : listArrTempt){
                List<Double> l = trackService.doubleToList(arr);
                lists.add(l);
            }
            weights = trackService.getWeight(lists);

        }
        return weights;
    }

    //获得为学生端服务的系统默认权重 计算JobSystemDefaultWeight
    public List<Double> getResumeJMRSystemDefaultWeight(){
        List<Double> weights = new ArrayList<>();
        List<Job> jobs = companyService.getAllFavoredJobs();
        int n = jobs.size();
        if (n != 0){
            List<List<Double>> lists = new ArrayList<>();//存最终结果，一个list<Double>存入一个特征的所有值
            List<double[]> listArr = new ArrayList<>();//存数据，一个double[]存入一个学生简历的对应数据
            List<double[]> listArrTempt = new ArrayList<>();//临时翻转数据，将学生简历的对应数据存在一个arr翻转为一个特征的所有值存在一个list
            for (Job job : jobs){
                double[] arr = transferComponent.transferJobAndCompanyToArray(job);
                listArr.add(arr);
            }
            int m = listArr.get(0).length;//一共有多少个特征，也是lists的长度，也就是有几个List<Double>。
            //初始化lists\listArrTempt
            for (int j = 0; j < m; j++){
                double[] arrTempt = new double[n];
                listArrTempt.add(arrTempt);
            }
            //将listArr数据存入listArrTempt
            int i = 0;
            for (double[] arr : listArr){
                for (int j = 0; j < m; j++){
                    listArrTempt.get(j)[i] = arr[j];
                }
                i++;
            }
            //将listArrTempt数据翻转到lists
            for (double[] arr : listArrTempt){
                List<Double> l = trackService.doubleToList(arr);
                lists.add(l);
            }
            weights = trackService.getWeight(lists);

        }
        return weights;
    }

    //transfer double[] to List<Double>
    public List<Double> doubleToList(double[] arr_double){
        int num = arr_double.length;
        Double[] arr_Double = new Double[num];
        for(int i = 0;i < num;i ++){
            arr_Double[i] = arr_double[i];//double[]转Double[]
        }
        List<Double> list = Arrays.asList(arr_Double);//Double[]转List
        return list;
    }

    //熵值法计算权重
    public List<Double> getWeight(List<List<Double>> list){
        List<Double> listSum = new ArrayList<>();	//用于存放每种指标下所有记录归一化后的和
        List<Double> gList = new ArrayList<>();	//用于存放每种指标的差异系数
        List<Double> wList = new ArrayList<>();	//用于存放每种指标的最终权重系数
        double sumLast = 0;
        double k = 1 / Math.log(list.get(0).size()); //计算k值 k= 1/ln(n)
        //数据归一化处理	(i-min)/(max-min)
        for (int i = 0; i < list.size(); i++) {
            double sum = 0;
            double max = Collections.max(list.get(i));
            double min = Collections.min(list.get(i));
            for (int j = 0; j <list.get(i).size(); j++) {
                double temp = (list.get(i).get(j) - min) / (max - min);
                sum += temp;
                list.get(i).set(j, temp);
            }
            listSum.add(sum);
        }

        //计算每项指标下每个记录所占比重
        for (int i = 0; i < list.size(); i++) {
            double sum = 0;	//每种指标下所有记录权重和
            for (int j = 0; j <list.get(i).size(); j++) {
                if(list.get(i).get(j) / listSum.get(i) == 0){
                    sum +=0;
                }else{
                    sum += (list.get(i).get(j) / listSum.get(i)) * Math.log(list.get(i).get(j) / listSum.get(i));
                }

            }
            //计算第i项指标的熵值
            double e = -k * sum;
            //计算第j项指标的差异系数
            double g = 1-e;
            if (!Double.isNaN(g)) sumLast += g;
            gList.add(g);
        }
        //计算每项指标的权重
        for (int i = 0; i < gList.size(); i++) {
            double d = gList.get(i) / sumLast;
            if (Double.isNaN(d)) wList.add(0.0);
            else wList.add(d);
        }
        return wList;
    }

    //系统定时执行
    //倾向跟踪获得系统默认权重
    public void TrendTrack(){
        List<Double> resumeWeights = trackService.getJobSMRSystemDefaultWeight();
        List<Double> jobWeights = trackService.getResumeJMRSystemDefaultWeight();
        System.out.println("jobSMRWeights:" + jobWeights);
        System.out.println("resumeJMRWeights:" + resumeWeights);
        companyService.addJobSystemDefaultWeight(jobWeights);
        studentService.addResumeSystemDefaultWeight(resumeWeights);
    }


    /*---------------倾向、单向、双向跟踪：报表统计------------------
    ----------------------------------------------------------*/
    //处理：统计报表:List<Student>
    public StudentListStatisticalFormVo getStudentListStatisticalFormVo(List<Student> students){
        StudentListStatisticalFormVo studentListStatisticalFormVo = new StudentListStatisticalFormVo();
        int n = students.size();
        int resumeTotalCount = 0;
        int resumeTotalHot = 0;

        int studentTotalCLevel = 0;
        int studentTotalHistory = 0;
        int studentTotalELanguage = 0;
        int studentTotalIfCareer = 0;
        int studentTotalIfProject = 0;
        int studentTotalIfFresh = 0;
        int resumeTotalPCount = 0;
        int resumeTotalCCount = 0;
        int resumeTotalHCount = 0;
        int resumeTotalSCount = 0;

        int resumeTotalBTrip = 0;
        int resumeTotalABonus = 0;
        int resumeTotalCUp = 0;
        int resumeTotalHSubside = 0;
        int resumeTotalInsurance = 0;
        int resumeTotalOAllowance = 0;
        int resumeTotalPLeave = 0;
        int resumeTotalStock = 0;
        int resumeTotalTSubside = 0;

        for (Student student : students) {
            List<Resume> resumes = studentService.getResumesByStudentId(student.getS_id());
            resumeTotalCount += resumes.size();
            for (Resume resume : resumes) {
                resumeTotalHot += companyService.getJobResumeCountByResume(resume.getR_id(),true);
                resumeTotalPCount += resume.getR_p_count();
                resumeTotalCCount = resume.getR_c_count();
                resumeTotalHCount = resume.getR_h_count();
                resumeTotalSCount = resume.getR_s_count();
                resumeTotalBTrip += resume.getR_if_b_trip().ordinal();
                resumeTotalABonus += resume.getR_if_a_bonus().ordinal();
                resumeTotalCUp += resume.getR_if_check_up().ordinal();
                resumeTotalHSubside += resume.getR_if_h_subside().ordinal();
                resumeTotalInsurance += resume.getR_if_insurance().ordinal();
                resumeTotalOAllowance += resume.getR_if_o_allowance().ordinal();
                resumeTotalPLeave += resume.getR_if_p_leave().ordinal();
                resumeTotalStock += resume.getR_if_stock().ordinal();
                resumeTotalTSubside += resume.getR_if_t_subside().ordinal();
            }
            studentTotalCLevel += student.getS_c_level().ordinal();
            studentTotalHistory += student.getS_e_history().ordinal();
            studentTotalELanguage += student.getS_e_language().ordinal();
            studentTotalIfCareer += student.getS_if_career().ordinal();
            studentTotalIfProject += student.getS_if_project_experience().ordinal();
            studentTotalIfFresh += student.getS_if_fresh().ordinal();
        }

        if (n == 0){
            studentListStatisticalFormVo.setStudentAveCLevel(valueComponent.getEWCLevel(5));
            studentListStatisticalFormVo.setStudentAveHistory(valueComponent.getEWEHistory(3));
            studentListStatisticalFormVo.setStudentAveELanguage(valueComponent.getEWELanguage(7));
            studentListStatisticalFormVo.setStudentAveIfCareer(valueComponent.getEWIsOrNot(1));
            studentListStatisticalFormVo.setStudentAveIfProject(valueComponent.getEWIsOrNot(1));
            studentListStatisticalFormVo.setStudentAveIfFresh(valueComponent.getEWIsOrNot(1));
        }
        else{
            studentListStatisticalFormVo.setStudentAveCLevel(valueComponent.getEWCLevel((int) Math.round((studentTotalCLevel+0.0)/n)));
            studentListStatisticalFormVo.setStudentAveHistory(valueComponent.getEWEHistory((int) Math.round((studentTotalHistory+0.0)/n)));
            studentListStatisticalFormVo.setStudentAveELanguage(valueComponent.getEWELanguage((int) Math.round((studentTotalELanguage+0.0)/n)));
            studentListStatisticalFormVo.setStudentAveIfCareer(valueComponent.getEWIsOrNot((int) Math.round((studentTotalIfCareer+0.0)/n)));
            studentListStatisticalFormVo.setStudentAveIfProject(valueComponent.getEWIsOrNot((int) Math.round((studentTotalIfProject+0.0)/n)));
            studentListStatisticalFormVo.setStudentAveIfFresh(valueComponent.getEWIsOrNot((int) Math.round((studentTotalIfFresh+0.0)/n)));
            studentListStatisticalFormVo.setResumeTotalCount(resumeTotalCount);
            studentListStatisticalFormVo.setResumeAveCount((int) Math.round((resumeTotalSCount+0.0)/n));
            studentListStatisticalFormVo.setResumeTotalHot(resumeTotalHot);
        }

        if(resumeTotalCount == 0){
            studentListStatisticalFormVo.setResumeAveBTrip(valueComponent.getEWIsOrNot(1));
            studentListStatisticalFormVo.setResumeAveABonus(valueComponent.getEWIsNeedOrNot(2));
            studentListStatisticalFormVo.setResumeAveCUp(valueComponent.getEWIsNeedOrNot(2));
            studentListStatisticalFormVo.setResumeAveHSubside(valueComponent.getEWIsNeedOrNot(2));
            studentListStatisticalFormVo.setResumeAveInsurance(valueComponent.getEWIsNeedOrNot(2));
            studentListStatisticalFormVo.setResumeAveOAllowance(valueComponent.getEWIsNeedOrNot(2));
            studentListStatisticalFormVo.setResumeAvePLeave(valueComponent.getEWIsNeedOrNot(2));
            studentListStatisticalFormVo.setResumeAveStock(valueComponent.getEWIsNeedOrNot(2));
            studentListStatisticalFormVo.setResumeAveTSubside(valueComponent.getEWIsNeedOrNot(2));
        }
        else{
            studentListStatisticalFormVo.setResumeAveHot((int) Math.round((resumeTotalHot+0.0)/resumeTotalCount));

            studentListStatisticalFormVo.setResumeAveCCount((int) Math.round((resumeTotalCCount+0.0)/resumeTotalCount));
            studentListStatisticalFormVo.setResumeAveHCount((int) Math.round((resumeTotalHCount+0.0)/resumeTotalCount));
            studentListStatisticalFormVo.setResumeAveSCount((int) Math.round((resumeTotalSCount+0.0)/resumeTotalCount));
            studentListStatisticalFormVo.setResumeAvePCount((int) Math.round((resumeTotalPCount+0.0)/resumeTotalCount));

            studentListStatisticalFormVo.setResumeAveBTrip(valueComponent.getEWIsOrNot((int) Math.round((resumeTotalBTrip+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAveABonus(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalABonus+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAveCUp(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalCUp+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAveHSubside(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalHSubside+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAveInsurance(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalInsurance+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAveOAllowance(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalOAllowance+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAvePLeave(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalPLeave+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAveStock(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalStock+0.0)/resumeTotalCount)));
            studentListStatisticalFormVo.setResumeAveTSubside(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalTSubside+0.0)/resumeTotalCount)));
        }

        System.out.println("studentListStatisticalFormVo:" + studentListStatisticalFormVo);
        return studentListStatisticalFormVo;
    }

    //处理：统计报表:List<Resume>
    public ResumeListStatisticalFormVo getResumeListStatisticalFormVo(List<Resume> resumes){
        ResumeListStatisticalFormVo resumeListStatisticalFormVo = new ResumeListStatisticalFormVo();
        int resumeTotalCount = resumes.size();
        int resumeTotalHot = 0;

        int studentTotalCLevel = 0;
        int studentTotalHistory = 0;
        int studentTotalELanguage = 0;
        int studentTotalIfCareer = 0;
        int studentTotalIfProject = 0;
        int studentTotalIfFresh = 0;
        int resumeTotalPCount = 0;
        int resumeTotalCCount = 0;
        int resumeTotalHCount = 0;
        int resumeTotalSCount = 0;

        int resumeTotalBTrip = 0;
        int resumeTotalABonus = 0;
        int resumeTotalCUp = 0;
        int resumeTotalHSubside = 0;
        int resumeTotalInsurance = 0;
        int resumeTotalOAllowance = 0;
        int resumeTotalPLeave = 0;
        int resumeTotalStock = 0;
        int resumeTotalTSubside = 0;

        for (Resume resume : resumes) {
            Student student = resume.getR_student();
            resumeTotalHot += companyService.getJobResumeCountByResume(resume.getR_id(),true);
            resumeTotalPCount += resume.getR_p_count();
            resumeTotalCCount = resume.getR_c_count();
            resumeTotalHCount = resume.getR_h_count();
            resumeTotalSCount = resume.getR_s_count();
            resumeTotalBTrip += resume.getR_if_b_trip().ordinal();
            resumeTotalABonus += resume.getR_if_a_bonus().ordinal();
            resumeTotalCUp += resume.getR_if_check_up().ordinal();
            resumeTotalHSubside += resume.getR_if_h_subside().ordinal();
            resumeTotalInsurance += resume.getR_if_insurance().ordinal();
            resumeTotalOAllowance += resume.getR_if_o_allowance().ordinal();
            resumeTotalPLeave += resume.getR_if_p_leave().ordinal();
            resumeTotalStock += resume.getR_if_stock().ordinal();
            resumeTotalTSubside += resume.getR_if_t_subside().ordinal();
            studentTotalCLevel += student.getS_c_level().ordinal();
            studentTotalHistory += student.getS_e_history().ordinal();
            studentTotalELanguage += student.getS_e_language().ordinal();
            studentTotalIfCareer += student.getS_if_career().ordinal();
            studentTotalIfProject += student.getS_if_project_experience().ordinal();
            studentTotalIfFresh += student.getS_if_fresh().ordinal();
        }

        if(resumeTotalCount == 0){
            resumeListStatisticalFormVo.setStudentAveCLevel(valueComponent.getEWCLevel(5));
            resumeListStatisticalFormVo.setStudentAveHistory(valueComponent.getEWEHistory(3));
            resumeListStatisticalFormVo.setStudentAveELanguage(valueComponent.getEWELanguage(7));
            resumeListStatisticalFormVo.setStudentAveIfCareer(valueComponent.getEWIsOrNot(1));
            resumeListStatisticalFormVo.setStudentAveIfProject(valueComponent.getEWIsOrNot(1));
            resumeListStatisticalFormVo.setStudentAveIfFresh(valueComponent.getEWIsOrNot(1));

            resumeListStatisticalFormVo.setResumeAveBTrip(valueComponent.getEWIsOrNot(1));
            resumeListStatisticalFormVo.setResumeAveABonus(valueComponent.getEWIsNeedOrNot(2));
            resumeListStatisticalFormVo.setResumeAveCUp(valueComponent.getEWIsNeedOrNot(2));
            resumeListStatisticalFormVo.setResumeAveHSubside(valueComponent.getEWIsNeedOrNot(2));
            resumeListStatisticalFormVo.setResumeAveInsurance(valueComponent.getEWIsNeedOrNot(2));
            resumeListStatisticalFormVo.setResumeAveOAllowance(valueComponent.getEWIsNeedOrNot(2));
            resumeListStatisticalFormVo.setResumeAvePLeave(valueComponent.getEWIsNeedOrNot(2));
            resumeListStatisticalFormVo.setResumeAveStock(valueComponent.getEWIsNeedOrNot(2));
            resumeListStatisticalFormVo.setResumeAveTSubside(valueComponent.getEWIsNeedOrNot(2));
        }
        else{
            resumeListStatisticalFormVo.setStudentAveCLevel(valueComponent.getEWCLevel((int) Math.round((studentTotalCLevel+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setStudentAveHistory(valueComponent.getEWEHistory((int) Math.round((studentTotalHistory+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setStudentAveELanguage(valueComponent.getEWELanguage((int) Math.round((studentTotalELanguage+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setStudentAveIfCareer(valueComponent.getEWIsOrNot((int) Math.round((studentTotalIfCareer+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setStudentAveIfProject(valueComponent.getEWIsOrNot((int) Math.round((studentTotalIfProject+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setStudentAveIfFresh(valueComponent.getEWIsOrNot((int) Math.round((studentTotalIfFresh+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeTotalCount(resumeTotalCount);
            resumeListStatisticalFormVo.setResumeTotalHot(resumeTotalHot);

            resumeListStatisticalFormVo.setResumeAveHot((int) Math.round((resumeTotalHot+0.0)/resumeTotalCount));

            resumeListStatisticalFormVo.setResumeAveCCount((int) Math.round((resumeTotalCCount+0.0)/resumeTotalCount));
            resumeListStatisticalFormVo.setResumeAveHCount((int) Math.round((resumeTotalHCount+0.0)/resumeTotalCount));
            resumeListStatisticalFormVo.setResumeAveSCount((int) Math.round((resumeTotalSCount+0.0)/resumeTotalCount));
            resumeListStatisticalFormVo.setResumeAvePCount((int) Math.round((resumeTotalPCount+0.0)/resumeTotalCount));

            resumeListStatisticalFormVo.setResumeAveBTrip(valueComponent.getEWIsOrNot((int) Math.round((resumeTotalBTrip+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAveABonus(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalABonus+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAveCUp(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalCUp+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAveHSubside(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalHSubside+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAveInsurance(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalInsurance+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAveOAllowance(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalOAllowance+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAvePLeave(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalPLeave+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAveStock(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalStock+0.0)/resumeTotalCount)));
            resumeListStatisticalFormVo.setResumeAveTSubside(valueComponent.getEWIsNeedOrNot((int) Math.round((resumeTotalTSubside+0.0)/resumeTotalCount)));
        }

        System.out.println("resumeListStatisticalFormVo:" + resumeListStatisticalFormVo);
        return resumeListStatisticalFormVo;
    }

    //处理：统计报表:List<Company>
    public CompanyListStatisticalFormVo getCompanyListStatisticalFormVo(List<Company> companies){
        CompanyListStatisticalFormVo companyListStatisticalFormVo = new CompanyListStatisticalFormVo();
        int n = companies.size();
        int jobTotalCount = 0;
        int jobTotalHot = 0;
        int companyTotalScale = 0;
        int companyTotalStage = 0;

        //岗位总体待遇
        int jobTotalSalary = 0;
        int jobTotalABonus = 0;
        int jobTotalCUp = 0;
        int jobTotalHSubside = 0;
        int jobTotalInsurance = 0;
        int jobTotalOAllowance = 0;
        int jobTotalPLeave = 0;
        int jobTotalStock = 0;
        int jobTotalTSubside = 0;

        //岗位总体要求
        int jobTotalCLevel = 0;
        int jobTotalHistory = 0;
        int jobTotalELanguage = 0;
        int jobTotalIfCareer = 0;
        int jobTotalIfProject = 0;
        int jobTotalIfFresh = 0;
        int jobTotalBTrip = 0;

        for (Company company : companies) {
            List<Job> jobs = companyService.getJobsByCompany(company.getC_id());
            companyTotalScale += company.getC_scale();
            companyTotalStage += company.getC_f_stage().ordinal();

            jobTotalCount += jobs.size();
            for (Job job : jobs) {
                jobTotalHot += studentService.getJobResumeCountByJob(job.getJ_id(), true);

                jobTotalSalary += job.getJ_s_range().ordinal();
                jobTotalABonus += job.getJ_a_bonus().ordinal();
                jobTotalCUp += job.getJ_check_up().ordinal();
                jobTotalHSubside += job.getJ_h_subside().ordinal();
                jobTotalInsurance += job.getJ_insurance().ordinal();
                jobTotalOAllowance += job.getJ_o_allowance().ordinal();
                jobTotalPLeave += job.getJ_p_leave().ordinal();
                jobTotalStock += job.getJ_stock().ordinal();
                jobTotalTSubside += job.getJ_t_subside().ordinal();

                jobTotalBTrip += job.getJ_b_trip().ordinal();
                jobTotalCLevel += job.getJ_c_level().ordinal();
                jobTotalHistory += job.getJ_e_history().ordinal();
                jobTotalELanguage += job.getJ_e_language().ordinal();
                jobTotalIfCareer += job.getJ_if_career().ordinal();
                jobTotalIfProject += job.getJ_if_project_experience().ordinal();
                jobTotalIfFresh += job.getJ_if_fresh().ordinal();
            }
        }

        if (n == 0){
            companyListStatisticalFormVo.setCompanyAveStage(valueComponent.getEWFStage(7));
        }
        else {
            companyListStatisticalFormVo.setJobTotalCount(jobTotalCount);
            companyListStatisticalFormVo.setJobAveCount((int) Math.round((jobTotalCount+0.0)/n));
            companyListStatisticalFormVo.setJobTotalHot(jobTotalHot);
            companyListStatisticalFormVo.setCompanyAveScale((int) Math.round((companyTotalScale+0.0)/n));
            companyListStatisticalFormVo.setCompanyAveStage(valueComponent.getEWFStage((int) Math.round((companyTotalStage+0.0)/n)));
        }

        if (jobTotalCount == 0){
            //岗位总体待遇
            companyListStatisticalFormVo.setJobAveSalary(valueComponent.getEWSRange(0));
            companyListStatisticalFormVo.setJobAveABonus(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveCUp(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveHSubside(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveInsurance(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveOAllowance(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAvePLeave(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveStock(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveTSubside(valueComponent.getEWIsOrNot(1));

            //岗位总体要求
            companyListStatisticalFormVo.setJobAveCLevel(valueComponent.getEWCLevel(5));
            companyListStatisticalFormVo.setJobAveHistory(valueComponent.getEWEHistory(3));
            companyListStatisticalFormVo.setJobAveELanguage(valueComponent.getEWELanguage(7));
            companyListStatisticalFormVo.setJobAveIfCareer(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveIfProject(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveIfFresh(valueComponent.getEWIsOrNot(1));
            companyListStatisticalFormVo.setJobAveBTrip(valueComponent.getEWIsNeedOrNot(0));
        }
        else {
            companyListStatisticalFormVo.setJobAveHot((int) Math.round((jobTotalHot+0.0)/jobTotalCount));

            //岗位总体待遇
            companyListStatisticalFormVo.setJobAveSalary(valueComponent.getEWSRange((int) Math.round((jobTotalSalary+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveABonus(valueComponent.getEWIsOrNot((int) Math.round((jobTotalABonus+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveCUp(valueComponent.getEWIsOrNot((int) Math.round((jobTotalCUp+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveHSubside(valueComponent.getEWIsOrNot((int) Math.round((jobTotalHSubside+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveInsurance(valueComponent.getEWIsOrNot((int) Math.round((jobTotalInsurance+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveOAllowance(valueComponent.getEWIsOrNot((int) Math.round((jobTotalOAllowance+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAvePLeave(valueComponent.getEWIsOrNot((int) Math.round((jobTotalPLeave+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveStock(valueComponent.getEWIsOrNot((int) Math.round((jobTotalStock+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveTSubside(valueComponent.getEWIsOrNot((int) Math.round((jobTotalTSubside+0.0)/jobTotalCount)));

            //岗位总体要求
            companyListStatisticalFormVo.setJobAveCLevel(valueComponent.getEWCLevel((int) Math.round((jobTotalCLevel+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveHistory(valueComponent.getEWEHistory((int) Math.round((jobTotalHistory+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveELanguage(valueComponent.getEWELanguage((int) Math.round((jobTotalELanguage+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveIfCareer(valueComponent.getEWIsOrNot((int) Math.round((jobTotalIfCareer+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveIfProject(valueComponent.getEWIsOrNot((int) Math.round((jobTotalIfProject+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveIfFresh(valueComponent.getEWIsOrNot((int) Math.round((jobTotalIfFresh+0.0)/jobTotalCount)));
            companyListStatisticalFormVo.setJobAveBTrip(valueComponent.getEWIsNeedOrNot((int) Math.round((jobTotalBTrip+0.0)/jobTotalCount)));
        }

        System.out.println("companyListStatisticalFormVo:" + companyListStatisticalFormVo);
        return companyListStatisticalFormVo;
    }

    //处理：统计报表:List<Job>
    public JobListStatisticalFormVo getJobListStatisticalFormVo(List<Job> jobs){
        JobListStatisticalFormVo jobListStatisticalFormVo = new JobListStatisticalFormVo();
        int jobTotalCount = jobs.size();
        int jobTotalHot = 0;
        int companyTotalScale = 0;
        int companyTotalStage = 0;

        //岗位总体待遇
        int jobTotalSalary = 0;
        int jobTotalABonus = 0;
        int jobTotalCUp = 0;
        int jobTotalHSubside = 0;
        int jobTotalInsurance = 0;
        int jobTotalOAllowance = 0;
        int jobTotalPLeave = 0;
        int jobTotalStock = 0;
        int jobTotalTSubside = 0;

        //岗位总体要求
        int jobTotalCLevel = 0;
        int jobTotalHistory = 0;
        int jobTotalELanguage = 0;
        int jobTotalIfCareer = 0;
        int jobTotalIfProject = 0;
        int jobTotalIfFresh = 0;
        int jobTotalBTrip = 0;


        for (Job job : jobs) {
            Company company = job.getJ_company();

            companyTotalScale += company.getC_scale();
            companyTotalStage += company.getC_f_stage().ordinal();
            jobTotalHot += studentService.getJobResumeCountByJob(job.getJ_id(), true);

            jobTotalSalary += job.getJ_s_range().ordinal();
            jobTotalABonus += job.getJ_a_bonus().ordinal();
            jobTotalCUp += job.getJ_check_up().ordinal();
            jobTotalHSubside += job.getJ_h_subside().ordinal();
            jobTotalInsurance += job.getJ_insurance().ordinal();
            jobTotalOAllowance += job.getJ_o_allowance().ordinal();
            jobTotalPLeave += job.getJ_p_leave().ordinal();
            jobTotalStock += job.getJ_stock().ordinal();
            jobTotalTSubside += job.getJ_t_subside().ordinal();

            jobTotalBTrip += job.getJ_b_trip().ordinal();
            jobTotalCLevel += job.getJ_c_level().ordinal();
            jobTotalHistory += job.getJ_e_history().ordinal();
            jobTotalELanguage += job.getJ_e_language().ordinal();
            jobTotalIfCareer += job.getJ_if_career().ordinal();
            jobTotalIfProject += job.getJ_if_project_experience().ordinal();
            jobTotalIfFresh += job.getJ_if_fresh().ordinal();
        }

        if (jobTotalCount == 0){
            //岗位总体待遇
            jobListStatisticalFormVo.setCompanyAveStage(valueComponent.getEWFStage(7));

            jobListStatisticalFormVo.setJobAveSalary(valueComponent.getEWSRange(0));
            jobListStatisticalFormVo.setJobAveABonus(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveCUp(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveHSubside(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveInsurance(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveOAllowance(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAvePLeave(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveStock(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveTSubside(valueComponent.getEWIsOrNot(1));

            //岗位总体要求
            jobListStatisticalFormVo.setJobAveCLevel(valueComponent.getEWCLevel(5));
            jobListStatisticalFormVo.setJobAveHistory(valueComponent.getEWEHistory(3));
            jobListStatisticalFormVo.setJobAveELanguage(valueComponent.getEWELanguage(7));
            jobListStatisticalFormVo.setJobAveIfCareer(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveIfProject(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveIfFresh(valueComponent.getEWIsOrNot(1));
            jobListStatisticalFormVo.setJobAveBTrip(valueComponent.getEWIsNeedOrNot(0));
        }
        else {
            jobListStatisticalFormVo.setJobTotalCount(jobTotalCount);
            jobListStatisticalFormVo.setJobTotalHot(jobTotalHot);
            jobListStatisticalFormVo.setCompanyAveScale((int) Math.round((companyTotalScale+0.0)/jobTotalCount));
            jobListStatisticalFormVo.setCompanyAveStage(valueComponent.getEWFStage((int) Math.round((companyTotalStage+0.0)/jobTotalCount)));

            jobListStatisticalFormVo.setJobAveHot((int) Math.round((jobTotalHot+0.0)/jobTotalCount));

            //岗位总体待遇
            jobListStatisticalFormVo.setJobAveSalary(valueComponent.getEWSRange((int) Math.round((jobTotalSalary+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveABonus(valueComponent.getEWIsOrNot((int) Math.round((jobTotalABonus+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveCUp(valueComponent.getEWIsOrNot((int) Math.round((jobTotalCUp+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveHSubside(valueComponent.getEWIsOrNot((int) Math.round((jobTotalHSubside+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveInsurance(valueComponent.getEWIsOrNot((int) Math.round((jobTotalInsurance+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveOAllowance(valueComponent.getEWIsOrNot((int) Math.round((jobTotalOAllowance+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAvePLeave(valueComponent.getEWIsOrNot((int) Math.round((jobTotalPLeave+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveStock(valueComponent.getEWIsOrNot((int) Math.round((jobTotalStock+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveTSubside(valueComponent.getEWIsOrNot((int) Math.round((jobTotalTSubside+0.0)/jobTotalCount)));

            //岗位总体要求
            jobListStatisticalFormVo.setJobAveCLevel(valueComponent.getEWCLevel((int) Math.round((jobTotalCLevel+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveHistory(valueComponent.getEWEHistory((int) Math.round((jobTotalHistory+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveELanguage(valueComponent.getEWELanguage((int) Math.round((jobTotalELanguage+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveIfCareer(valueComponent.getEWIsOrNot((int) Math.round((jobTotalIfCareer+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveIfProject(valueComponent.getEWIsOrNot((int) Math.round((jobTotalIfProject+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveIfFresh(valueComponent.getEWIsOrNot((int) Math.round((jobTotalIfFresh+0.0)/jobTotalCount)));
            jobListStatisticalFormVo.setJobAveBTrip(valueComponent.getEWIsNeedOrNot((int) Math.round((jobTotalBTrip+0.0)/jobTotalCount)));
        }

        System.out.println("jobListStatisticalFormVo:" + jobListStatisticalFormVo);
        return jobListStatisticalFormVo;
    }

    //用户调用接口：
    public Map trackCollegesByCollegeName(String collegeName){
        //获取需要跟踪的学生集合
        List<Student> students = studentService.getStudentsByCollege(collegeName);

        //学生集合 统计
        int n = students.size();
        StudentListStatisticalFormVo studentListStatisticalFormVo = trackService.getStudentListStatisticalFormVo(students);

        //倾向跟踪
        List<StudentFavoredJob> studentFavoredJobs = studentService.getStudentFavoredJobsByStudents(students);
        List<Job> jobsFavored = companyService.getJobsByStudentFavoredJobs(studentFavoredJobs);
        int fTCount = studentFavoredJobs.size();
        studentListStatisticalFormVo.setFavoredTotalCount(fTCount);
        studentListStatisticalFormVo.setFavoredAveCount((int) Math.round((fTCount+0.0)/n));
        JobListStatisticalFormVo jobListStatisticalFormVoFavored = trackService.getJobListStatisticalFormVo(jobsFavored);

        //单向跟踪
        List<JobResume> jobResumesOneWay = studentService.getJobResumesByStudents_ResumeToJob(students,true);
        List<Job> jobsOneWay = companyService.getJobsByJobResumes(jobResumesOneWay);
        int OTCount = jobResumesOneWay.size();
        studentListStatisticalFormVo.setOneWayTotalCount(OTCount);
        studentListStatisticalFormVo.setOneWayAveCount((int) Math.round((OTCount+0.0)/n));
        JobListStatisticalFormVo jobListStatisticalFormVoOneWay = trackService.getJobListStatisticalFormVo(jobsOneWay);

        //双向跟踪
        List<JobResume> jobResumesTwoWay = studentService.getJobResumesByStudents_ResumeToJob_JobToResume(students,true,true);
        List<Job> jobsTwoWay = companyService.getJobsByJobResumes(jobResumesTwoWay);
        int TTCount = jobResumesTwoWay.size();
        studentListStatisticalFormVo.setTwoWayTotalCount(TTCount);
        studentListStatisticalFormVo.setTwoWayAveCount((int) Math.round((TTCount+0.0)/n));
        JobListStatisticalFormVo jobListStatisticalFormVoTwoWay = trackService.getJobListStatisticalFormVo(jobsTwoWay);

        return Map.of("students",students,
                "StudentListStatisticalFormVo",studentListStatisticalFormVo,
                "jobListStatisticalFormVoFavored",jobListStatisticalFormVoFavored,
                "jobListStatisticalFormVoOneWay",jobListStatisticalFormVoOneWay,
                "jobListStatisticalFormVoTwoWay",jobListStatisticalFormVoTwoWay
                );

    }

    public Map trackCompaniesByFStage(EnumWarehouse.FINANCING_STAGE financingStage){
        //获取需要跟踪的企业集合
        List<Company> companies = companyService.getCompaniesByFStage(financingStage);

        //企业集合 统计
        int n = companies.size();
        CompanyListStatisticalFormVo companyListStatisticalFormVo = trackService.getCompanyListStatisticalFormVo(companies);

        //倾向跟踪
        List<CompanyFavoredResume> companyFavoredResumes = companyService.getCompanyFavoredResumesByCompanies(companies);
        List<Resume> resumesFavored = studentService.getResumesByCompanyFavoredResumes(companyFavoredResumes);
        int fTCount = companyFavoredResumes.size();
        companyListStatisticalFormVo.setFavoredTotalCount(fTCount);
        companyListStatisticalFormVo.setFavoredAveCount((int) Math.round((fTCount+0.0)/n));
        ResumeListStatisticalFormVo resumeListStatisticalFormVoFavored = trackService.getResumeListStatisticalFormVo(resumesFavored);

        //单向跟踪
        List<JobResume> jobResumesOneWay = companyService.getJobResumesByCompanies_JobToResume(companies,true);
        List<Resume> resumesOneWay = studentService.getResumesByJobResumes(jobResumesOneWay);
        int OTCount = jobResumesOneWay.size();
        companyListStatisticalFormVo.setOneWayTotalCount(OTCount);
        companyListStatisticalFormVo.setOneWayAveCount((int) Math.round((OTCount+0.0)/n));
        ResumeListStatisticalFormVo resumeListStatisticalFormVoOneWay = trackService.getResumeListStatisticalFormVo(resumesOneWay);


        //双向跟踪
        List<JobResume> jobResumesTwoWay = companyService.getJobResumesByCompanies_JobToResume_ResumeToJob(companies,true,true);
        List<Resume> resumesTwoWay = studentService.getResumesByJobResumes(jobResumesTwoWay);
        int TTCount = jobResumesTwoWay.size();
        companyListStatisticalFormVo.setTwoWayTotalCount(TTCount);
        companyListStatisticalFormVo.setTwoWayAveCount((int) Math.round((TTCount+0.0)/n));
        ResumeListStatisticalFormVo resumeListStatisticalFormVoTwoWay = trackService.getResumeListStatisticalFormVo(resumesTwoWay);

        return Map.of("companies",companies,
                "companyListStatisticalFormVo",companyListStatisticalFormVo,
                "resumeListStatisticalFormVoFavored",resumeListStatisticalFormVoFavored,
                "resumeListStatisticalFormVoOneWay",resumeListStatisticalFormVoOneWay,
                "resumeListStatisticalFormVoTwoWay",resumeListStatisticalFormVoTwoWay
        );

    }
}
