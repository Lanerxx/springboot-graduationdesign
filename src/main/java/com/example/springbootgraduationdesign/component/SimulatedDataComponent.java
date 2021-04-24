package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.component.vo.JobVo;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SimulatedDataComponent {
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SimulatedDataComponent simulatedDataComponent;
    @Autowired
    private CompanyService companyService;

    private static final int PROFESSION_COUNT = 15;
    private static final int INDUSTRY_COUNT = 5;
    private static final int POSITION_COUNT = 5;
    private static final int STUDENT_RANDOM_COUNT = 1000;
    private static final int STUDENT_COPY_COUNT = 5;
    private static final int COMPANY_RANDOM_COUNT = 50;

    private int FIRST_NAME_COUNT = FIRST_NAME.length;
    private int ADDRESS_COUNT = ADDRESS.length;
    private int FIGURE_THREE_COUNT = FIGURE_THREE.length;
    private int FIGURE_FOUR_COUNT = FIGURE_FOUR.length;
    private int EMAIL_END_COUNT = EMAIL_END.length;
    private int LETTER_COUNT = LETTER.length;

    private static final String[] FIRST_NAME={"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许",
            "何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎",
            "鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤","滕","殷",
            "罗","毕","郝","邬","安","常","乐","于","时","傅","皮","卞","齐","康","伍","余","元","卜","顾","孟","平","黄","和",
            "穆","萧","尹","姚","邵","湛","汪","祁","毛","禹","狄","米","贝","明","臧","计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒",
            "屈","项","祝","董","梁","杜","阮","蓝","闵","席","季","麻","强","贾","路","娄","危","江","童","颜","郭","梅","盛","林","刁","钟",
            "徐","邱","骆","高","夏","蔡","田","樊","胡","凌","霍","虞","万","支","柯","昝","管","卢","莫","经","房","裘","缪","干","解","应",
            "宗","丁","宣","贲","邓","郁","单","杭","洪","包","诸","左","石","崔","吉","钮","龚","程","嵇","邢","滑","裴","陆","荣","翁","荀",
            "羊","于","惠","甄","曲","家","封","芮","羿","储","靳","汲","邴","糜","松","井","段","富","巫","乌","焦","巴","弓","牧","隗","山",
            "谷","车","侯","宓","蓬","全","郗","班","仰","秋","仲","伊","宫","宁","仇","栾","暴","甘","钭","厉","戎","祖","武","符","刘","景",
            "詹","束","龙","叶","幸","司","韶","郜","黎","蓟","溥","印","宿","白","怀","蒲","邰","从","鄂","索","咸","籍","赖","卓","蔺","屠",
            "蒙","池","乔","阴","郁","胥","能","苍","双","闻","莘","党","翟","谭","贡","劳","逄","姬","申","扶","堵","冉","宰","郦","雍","却",
            "璩","桑","桂","濮","牛","寿","通","边","扈","燕","冀","浦","尚","农","温","别","庄","晏","柴","瞿","阎","充","慕","连","茹","习",
            "宦","艾","鱼","容","向","古","易","慎","戈","廖","庾","终","暨","居","衡","步","都","耿","满","弘","匡","国","文","寇","广","禄",
            "阙","东","欧","殳","沃","利","蔚","越","夔","隆","师","巩","厍","聂","晁","勾","敖","融","冷","訾","辛","阚","那","简","饶","空",
            "曾","毋","沙","乜","养","鞠","须","丰","巢","关","蒯","相","查","后","荆","红","游","郏","竺","权","逯","盖","益","桓","公","仉",
            "督","岳","帅","缑","亢","况","郈","有","琴","归","海","晋","楚","闫","法","汝","鄢","涂","钦","商","牟","佘","佴","伯","赏","墨",
            "哈","谯","篁","年","爱","阳","佟","言","福","南","火","铁","迟","漆","官","冼","真","展","繁","檀","祭","密","敬","揭","舜","楼",
            "疏","冒","浑","挚","胶","随","高","皋","原","种","练","弥","仓","眭","蹇","覃","阿","门","恽","来","綦","召","仪","风","介","巨",
            "木","京","狐","郇","虎","枚","抗","达","杞","苌","折","麦","庆","过","竹","端","鲜","皇","亓","老","是","秘","畅","邝","还","宾",
            "闾","辜","纵","侴","万俟","司马","上官","欧阳","夏侯","诸葛","闻人","东方","赫连","皇甫","羊舌","尉迟","公羊","澹台","公冶","宗正",
            "濮阳","淳于","单于","太叔","申屠","公孙","仲孙","轩辕","令狐","钟离","宇文","长孙","慕容","鲜于","闾丘","司徒","司空","兀官","司寇",
            "南门","呼延","子车","颛孙","端木","巫马","公西","漆雕","车正","壤驷","公良","拓跋","夹谷","宰父","谷梁","段干","百里","东郭","微生",
            "梁丘","左丘","东门","西门","南宫","第五","公仪","公乘","太史","仲长","叔孙","屈突","尔朱","东乡","相里","胡母","司城","张廖","雍门",
            "毋丘","贺兰","綦毋","屋庐","独孤","南郭","北宫","王孙"};
    private static final String[] ADDRESS = {"合肥","安庆", "安庆","蚌埠", "蚌埠","亳州", "亳州","巢湖", "巢湖","滁州", "滁州","阜阳", "阜阳","贵池", "贵池",
            "淮北", "淮北","淮化", "淮化","淮南", "淮南","黄山", "黄山","九华山", "九华山","六安", "六安","马鞍山", "马鞍山","宿州", "宿州","铜陵",
            "铜陵","屯溪", "屯溪","芜湖", "芜湖","宣城", "宣城","东城", "东城","西城", "西城","崇文", "崇文","宣武", "宣武","朝阳", "朝阳","丰台",
            "丰台","石景山", "石景山","海淀", "海淀","门头沟", "门头沟","房山", "房山","通州", "通州","顺义", "顺义","昌平", "昌平","大兴", "大兴","平谷",
            "平谷","怀柔", "怀柔","密云", "密云","延庆", "延庆","万州", "万州","涪陵", "涪陵","渝中", "渝中","大渡口", "大渡口","江北", "江北","沙坪坝",
            "沙坪坝","九龙坡","九龙坡","南岸", "南岸","北碚", "北碚","万盛", "万盛","双挢", "双挢","渝北", "渝北","巴南", "巴南","黔江", "黔江","长寿",
            "长寿","綦江", "綦江","潼南", "潼南","铜梁", "铜梁","大足", "大足","荣昌", "荣昌","壁山", "壁山","梁平", "梁平","城口", "城口","丰都", "丰都",
            "垫江", "垫江","武隆", "武隆","忠县", "忠县","开县", "开县","云阳", "云阳","奉节", "奉节","巫山", "巫山","巫溪", "巫溪","石柱", "石柱",
    };
    private static final String[] LETTER = {"Q","W","E","R","T","Y","U","I","O","P",
            "A","S","D","F","G","H","J","K","L",
    "Z","X","C","V","B","N","M"};
    private static final String[] FIGURE_THREE = {"139","159","153","158","133","137","188","155","131","154"};
    private static final String[] FIGURE_FOUR = {"2139","1159","7153","8158","0133","9137","2188","5155","4131","3154"};
    private static final String[] EMAIL_END = {"@qq.com","@163.com","@nefu.cn.com","@168.cn","@xinlang.cn","@alibaba.com","@baiduyun.cn"};

    public void  simulatedProfession(){
        Profession profession = new Profession();
        profession.setPr_m_class("哲学类");
        profession.setPr_s_class("哲学");
        professionService.addProfession(profession);

        Profession profession1 = new Profession();
        profession1.setPr_m_class("哲学类");
        profession1.setPr_s_class("逻辑学");
        professionService.addProfession(profession1);

        Profession profession2 = new Profession();
        profession2.setPr_m_class("哲学类");
        profession2.setPr_s_class("伦理学");
        professionService.addProfession(profession2);

        Profession profession3 = new Profession();
        profession3.setPr_m_class("计算机类");
        profession3.setPr_s_class("软件工程");
        professionService.addProfession(profession3);

        Profession profession4 = new Profession();
        profession4.setPr_m_class("计算机类");
        profession4.setPr_s_class("计算机科学与技术");
        professionService.addProfession(profession4);

        Profession profession5 = new Profession();
        profession5.setPr_m_class("计算机类");
        profession5.setPr_s_class("信息管理");
        professionService.addProfession(profession5);

        Profession profession6 = new Profession();
        profession6.setPr_m_class("电气信息类");
        profession6.setPr_s_class("自动化");
        professionService.addProfession(profession6);

        Profession profession7 = new Profession();
        profession7.setPr_m_class("电气信息类");
        profession7.setPr_s_class("电子信息工程");
        professionService.addProfession(profession7);

        Profession profession8 = new Profession();
        profession8.setPr_m_class("电气信息类");
        profession8.setPr_s_class("通信工程");
        professionService.addProfession(profession8);

        Profession profession9 = new Profession();
        profession9.setPr_m_class("教育类");
        profession9.setPr_s_class("小学教育");
        professionService.addProfession(profession9);

        Profession profession10 = new Profession();
        profession10.setPr_m_class("教育类");
        profession10.setPr_s_class("艺术教育");
        professionService.addProfession(profession10);

        Profession profession11 = new Profession();
        profession11.setPr_m_class("教育类");
        profession11.setPr_s_class("人文教育");
        professionService.addProfession(profession11);

        Profession profession12 = new Profession();
        profession12.setPr_m_class("教育类");
        profession12.setPr_s_class("科学教育");
        professionService.addProfession(profession12);

        Profession profession13 = new Profession();
        profession13.setPr_m_class("外语类");
        profession13.setPr_s_class("日语");
        professionService.addProfession(profession13);

        Profession profession14 = new Profession();
        profession14.setPr_m_class("外语类");
        profession14.setPr_s_class("法语");
        professionService.addProfession(profession14);

        Profession profession15 = new Profession();
        profession15.setPr_m_class("外语类");
        profession15.setPr_s_class("西班牙语");
        professionService.addProfession(profession15);
    }

    public void simulatedIndustry(){
        Industry industry = new Industry();
        industry.setI_name("办公文教");
        industryService.addIndustry(industry);

        Industry industry1 = new Industry();
        industry1.setI_name("信息产业");
        industryService.addIndustry(industry1);

        Industry industry2 = new Industry();
        industry2.setI_name("机械机电");
        industryService.addIndustry(industry2);

        Industry industry3 = new Industry();
        industry3.setI_name("旅游休闲");
        industryService.addIndustry(industry3);

        Industry industry4 = new Industry();
        industry4.setI_name("电子电工");
        industryService.addIndustry(industry4);
    }

    public void simulatedPosition(){
        Random random = new Random();
        for (int i = 0; i < POSITION_COUNT; i++){
            Position position = new Position();
            int iid = random.nextInt(INDUSTRY_COUNT) +1;
            Industry industry = industryService.getIndustry(iid);
            position.setPo_industry(industry);
            position.setPo_name(industry.getI_name() + "的岗位"+i);
            positionService.addPosition(position);
        }
    }

    public void simulatedStudent(){
        Random random = new Random();
        for (int i = 0; i < STUDENT_RANDOM_COUNT; i++){
            int prid = random.nextInt(PROFESSION_COUNT) + 1;
            Student student = new Student();
            student.setS_password("123456");
            student.setS_profession(professionService.getProfession(prid));
            student.setS_name(FIRST_NAME[random.nextInt(FIRST_NAME_COUNT)] + "学生");
            student.setS_birthday(random.nextInt(20) + 1990 + "" + (random.nextInt(12) + 1));//1945-2004
            student.setS_college(ADDRESS[random.nextInt(ADDRESS_COUNT)]  + FIRST_NAME[random.nextInt(FIRST_NAME_COUNT)]  + "大学" + i);
            student.setS_place(ADDRESS[random.nextInt(ADDRESS_COUNT)] + "省" + ADDRESS[random.nextInt(ADDRESS_COUNT)] + "市");
            student.setS_g_time("202" + random.nextInt(3) + "09");
            student.setS_telephone(FIGURE_THREE[random.nextInt(FIGURE_THREE_COUNT)] + FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)] + FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)]);
            student.setS_email(FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)] + EMAIL_END[random.nextInt(EMAIL_END_COUNT)]);
            student.setS_gpa(random.nextInt(34) + 60);//60-93

            if (random.nextInt(2) == 0)
                student.setS_gender(EnumWarehouse.GENDER.MALE);
            else
                student.setS_gender(EnumWarehouse.GENDER.FEMALE);

            int temp = random.nextInt(6);
            int temp1 = random.nextInt(3);
            switch (temp){
                case 0://985- 1/3博士 - 1/3硕士 - 1/3 学士
                    student.setS_c_level(EnumWarehouse.C_LEVEL._985);
                    if (temp1 == 0)
                        student.setS_e_history(EnumWarehouse.E_HISTORY.DOCTOR);
                    else if (temp1 == 1)
                        student.setS_e_history(EnumWarehouse.E_HISTORY.MASTER);
                    else
                        student.setS_e_history(EnumWarehouse.E_HISTORY.BACHELOR);
                    break;
                case 1://211- 1/3博士 - 1/3硕士 - 1/3 学士
                    student.setS_c_level(EnumWarehouse.C_LEVEL._211);
                    if (temp1 == 0)
                        student.setS_e_history(EnumWarehouse.E_HISTORY.DOCTOR);
                    else if (temp1 == 1)
                        student.setS_e_history(EnumWarehouse.E_HISTORY.MASTER);
                    else
                        student.setS_e_history(EnumWarehouse.E_HISTORY.BACHELOR);
                    break;
                case 2://一本- 2/3硕士 - 1/3 学士
                    student.setS_c_level(EnumWarehouse.C_LEVEL.FIRST_UNDERGRADUATE);
                    if (temp1 == 0)
                        student.setS_e_history(EnumWarehouse.E_HISTORY.BACHELOR);
                    else
                        student.setS_e_history(EnumWarehouse.E_HISTORY.MASTER);
                    break;
                case 3://二本- 1/3硕士 - 2/3 学士
                    student.setS_c_level(EnumWarehouse.C_LEVEL.SECOND_UNDERGRADUATE);
                    if (temp1 == 0)
                        student.setS_e_history(EnumWarehouse.E_HISTORY.MASTER);
                    else
                        student.setS_e_history(EnumWarehouse.E_HISTORY.BACHELOR);
                    break;
                case 4://专科- 专科
                    student.setS_c_level(EnumWarehouse.C_LEVEL.JUNIOR_COLLEGE);
                    student.setS_e_history(EnumWarehouse.E_HISTORY.JUNIOR_COLLEGE);
                    break;
                case 5://职高- 专科
                    student.setS_c_level(EnumWarehouse.C_LEVEL.VOCATIONAL_AND_TECHNICAL_COLLEGE);
                    student.setS_e_history(EnumWarehouse.E_HISTORY.JUNIOR_COLLEGE);
                    break;
            }

            temp = random.nextInt(7);
            switch (temp){
                case 0:
                    student.setS_e_language(EnumWarehouse.E_LANGUAGE.FOREIGN_EXPERIENCE);
                    break;
                case 1:
                    student.setS_e_language(EnumWarehouse.E_LANGUAGE.TOEFL_IELTS);
                    break;
                case 2:
                    student.setS_e_language(EnumWarehouse.E_LANGUAGE.TEM8);
                    break;
                case 3:
                    student.setS_e_language(EnumWarehouse.E_LANGUAGE.TEM4);
                    break;
                case 4:
                    student.setS_e_language(EnumWarehouse.E_LANGUAGE.CET6);
                    break;
                case 5:
                    student.setS_e_language(EnumWarehouse.E_LANGUAGE.CET4);
                    break;
                case 6:
                    student.setS_e_language(EnumWarehouse.E_LANGUAGE.NONE);
                    break;
            }

            temp = random.nextInt(4);
            switch (temp){
                case 0:
                    student.setS_f_language(EnumWarehouse.F_LANGUAGE.FOREIGN_EXPERIENCE);
                    break;
                case 1:
                    student.setS_f_language(EnumWarehouse.F_LANGUAGE.N2);
                    break;
                case 2:
                    student.setS_f_language(EnumWarehouse.F_LANGUAGE.N1);
                    break;
                case 3:
                    student.setS_f_language(EnumWarehouse.F_LANGUAGE.NONE);
                    break;
            }

            temp = random.nextInt(5);
            switch (temp){
                case 0:
                    student.setS_s_range(EnumWarehouse.S_RANGE._4);
                    break;
                case 1:
                    student.setS_s_range(EnumWarehouse.S_RANGE._4_6);
                    break;
                case 2:
                    student.setS_s_range(EnumWarehouse.S_RANGE._6_8);
                    break;
                case 3:
                    student.setS_s_range(EnumWarehouse.S_RANGE._8_10);
                    break;
                case 4:
                    student.setS_s_range(EnumWarehouse.S_RANGE._10);
                    break;
            }

            temp = random.nextInt(5);
            switch (temp){
                case 0:
                    student.setS_ranking(EnumWarehouse.RANKING._0_10);
                    break;
                case 1:
                    student.setS_ranking(EnumWarehouse.RANKING._10_20);
                    break;
                case 2:
                    student.setS_ranking(EnumWarehouse.RANKING._20_30);
                    break;
                case 3:
                    student.setS_ranking(EnumWarehouse.RANKING._30_40);
                    break;
                case 4:
                    student.setS_ranking(EnumWarehouse.RANKING._40);
                    break;
            }

            student.setS_if_work(EnumWarehouse.IF_IS_OR_NOT.NO);
            student.setS_w_province("无");
            student.setS_company("无");

            if (random.nextInt(2) == 0)
                student.setS_if_career(EnumWarehouse.IF_IS_OR_NOT.YES);
            else
                student.setS_if_career(EnumWarehouse.IF_IS_OR_NOT.NO);

            if (random.nextInt(2) == 0)
                student.setS_if_fresh(EnumWarehouse.IF_IS_OR_NOT.YES);
            else
                student.setS_if_fresh(EnumWarehouse.IF_IS_OR_NOT.NO);

            if (random.nextInt(2) == 0)
                student.setS_if_project_experience(EnumWarehouse.IF_IS_OR_NOT.YES);
            else
                student.setS_if_project_experience(EnumWarehouse.IF_IS_OR_NOT.NO);

            studentService.addStudent(student);

            Student student1 = new Student();
            student1 = simulatedDataComponent.copyStudent(student, student1);
            studentService.addStudent(student1);

            Student student2 = new Student();
            student2 = simulatedDataComponent.copyStudent(student, student2);
            studentService.addStudent(student2);

            Student student3 = new Student();
            student3 = simulatedDataComponent.copyStudent(student, student3);
            studentService.addStudent(student3);

            Student student4 = new Student();
            student4 = simulatedDataComponent.copyStudent(student, student4);
            studentService.addStudent(student4);
        }
    }

    public Student copyStudent(Student studentPro, Student studentTar) {
        studentTar.setS_password("123456");
        studentTar.setS_profession(studentPro.getS_profession());
        studentTar.setS_name(studentPro.getS_name());
        studentTar.setS_birthday(studentPro.getS_birthday());
        studentTar.setS_college(studentPro.getS_college());
        studentTar.setS_place(studentPro.getS_place());
        studentTar.setS_g_time(studentPro.getS_g_time());
        studentTar.setS_telephone(studentPro.getS_telephone());
        studentTar.setS_email(studentPro.getS_email());
        studentTar.setS_gpa(studentPro.getS_gpa());
        studentTar.setS_gender(studentPro.getS_gender());
        studentTar.setS_c_level(studentPro.getS_c_level());
        studentTar.setS_e_history(studentPro.getS_e_history());
        studentTar.setS_e_language(studentPro.getS_e_language());
        studentTar.setS_f_language(studentPro.getS_f_language());
        studentTar.setS_s_range(studentPro.getS_s_range());
        studentTar.setS_ranking(studentPro.getS_ranking());
        studentTar.setS_if_work(studentPro.getS_if_work());
        studentTar.setS_w_province(studentPro.getS_w_province());
        studentTar.setS_company(studentPro.getS_company());
        studentTar.setS_if_career(studentPro.getS_if_career());
        studentTar.setS_if_fresh(studentPro.getS_if_fresh());
        studentTar.setS_if_project_experience(studentPro.getS_if_project_experience());
        return studentTar;
    }

    public void simulatedStudentIndustry(){
        List<Student> students = studentService.getAllStudents();
        Random random = new Random();
        for (Student student : students) {
            int temp = random.nextInt(3);
            for (int i = 0; i <= temp; i++){
                Industry industry = industryService.getIndustry(random.nextInt(INDUSTRY_COUNT) + 1);
                StudentIndustry studentIndustry = new StudentIndustry();
                StudentIndustryPK studentIndustryPK = new StudentIndustryPK();
                studentIndustryPK.setSi_student(student);
                studentIndustryPK.setSi_industry(industry);
                studentIndustry.setStudentIndustryPK(studentIndustryPK);
                studentService.addStudentIndustry(studentIndustry);
            }
        }
    }

    public void simulatedStudentPosition(){
        List<Student> students = studentService.getAllStudents();
        Random random = new Random();
        for (Student student : students) {
            int temp = random.nextInt(4);
            for (int i = 0; i <= temp; i++){
                Position position = positionService.getPosition(random.nextInt(POSITION_COUNT) + 1);
                StudentPosition studentPosition = new StudentPosition();
                StudentPositionPK studentPositionPK = new StudentPositionPK();
                studentPositionPK.setSp_student(student);
                studentPositionPK.setSp_position(position);
                studentPosition.setStudentPositionPK(studentPositionPK);
                studentService.addStudentPosition(studentPosition);
            }
        }
    }

    public Resume simulatedResume(Student student){
        Random random = new Random();
        Resume resume = new Resume();
        resume.setR_student(student);
        resume.setR_e_location(ADDRESS[random.nextInt(ADDRESS_COUNT)] + "省" + ADDRESS[random.nextInt(ADDRESS_COUNT)] + "市");
        resume.setR_m_course("主要课程·······");
        resume.setR_p_count(random.nextInt(4));
        resume.setR_paper("论文的详细介绍");
        resume.setR_c_count(random.nextInt(4));
        resume.setR_certificate("证书的详细介绍");
        resume.setR_s_count(random.nextInt(4));
        resume.setR_skill("技能的详细介绍");
        resume.setR_h_count(random.nextInt(4));
        resume.setR_honor("荣誉详细介绍");
        resume.setR_career("工作/实习经历的详细介绍");
        resume.setR_p_experience("项目经验的详细介绍");
        resume.setR_s_evaluate("个人评估");
        resume.setR_remark("备注");
        resume.setR_count(0);
        resume.setPosted(false);
        int temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_insurance(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_insurance(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_insurance(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_check_up(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_check_up(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_check_up(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_a_bonus(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_a_bonus(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_a_bonus(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_p_leave(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_p_leave(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_p_leave(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_o_allowance(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_o_allowance(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_o_allowance(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_stock(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_stock(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_stock(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_t_subside(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_t_subside(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_t_subside(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                resume.setR_if_h_subside(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                resume.setR_if_h_subside(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                resume.setR_if_h_subside(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }

        temp = random.nextInt(2);
        switch (temp){
            case 0:
                resume.setR_if_b_trip(EnumWarehouse.IF_IS_OR_NOT.YES);
                break;
            case 1:
                resume.setR_if_b_trip(EnumWarehouse.IF_IS_OR_NOT.NO);
                break;
        }
        return resume;
    }

    public void simulatedStudentResume(){
        System.out.println("========simulatedStudentResume=======");
        List<Student> students = studentService.getAllStudents();
        System.out.println("students:" + students.size());
        Random random = new Random();
        for (Student student : students) {
            int temp = random.nextInt(4);
            for (int i = 0; i <= temp; i++){
                Resume resume = simulatedDataComponent.simulatedResume(student);
                studentService.addResume(resume);
                if (random.nextInt(2) == 0){
                    resume.setPosted(true);
                    studentService.updateResume(resume);
                    StudentResume studentResume = new StudentResume();
                    StudentResumePK studentResumePK = new StudentResumePK();
                    studentResumePK.setSr_student(student);
                    studentResumePK.setSr_resume(resume);
                    studentResume.setStudentResumePK(studentResumePK);
                    studentService.addStudentResume(studentResume);
                }
            }
        }

    }

    public void simulatedCompany(){
        Random random = new Random();
        for (int i = 0; i < COMPANY_RANDOM_COUNT; i++){
            Company company = new Company();
            Industry industry = industryService.getIndustry(random.nextInt(INDUSTRY_COUNT) + 1);
            company.setC_industry(industry);
            company.setC_name(ADDRESS[random.nextInt(ADDRESS_COUNT)] + "公司");
            company.setC_password("123456");
            company.setC_s_code(LETTER[random.nextInt(LETTER_COUNT)] + LETTER[random.nextInt(LETTER_COUNT)] +
                    FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)] + LETTER[random.nextInt(LETTER_COUNT)] + FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)]);
            company.setC_description("描述");
            company.setC_f_contact(FIRST_NAME[random.nextInt(FIRST_NAME_COUNT)] + "联系人1");
            company.setC_f_telephone(FIGURE_THREE[random.nextInt(FIGURE_THREE_COUNT)] + FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)] + FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)]);
            company.setC_s_contact(FIRST_NAME[random.nextInt(FIRST_NAME_COUNT)] + "联系人2");
            company.setC_s_telephone(FIGURE_THREE[random.nextInt(FIGURE_THREE_COUNT)] + FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)] + FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)]);
            company.setC_email(FIGURE_FOUR[random.nextInt(FIGURE_FOUR_COUNT)] + EMAIL_END[random.nextInt(EMAIL_END_COUNT)]);
            company.setC_location(ADDRESS[random.nextInt(ADDRESS_COUNT)] + "省" + ADDRESS[random.nextInt(ADDRESS_COUNT)] + "市");
            company.setC_e_date(random.nextInt(2) + 2021 + "" + (random.nextInt(12) + 1));
            company.setC_scale(random.nextInt(2000));

            int temp1 = random.nextInt(8);
            switch (temp1){
                case 0:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.LISTED);
                    break;
                case 1:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.D_ROUND);
                    break;
                case 2:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.C_ROUND);
                    break;
                case 3:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.B_ROUND);
                    break;
                case 4:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.A_ROUND);
                    break;
                case 5:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.ANGEL_ROUND);
                    break;
                case 6:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.SEED_ROUND);
                    break;
                case 7:
                    company.setC_f_stage(EnumWarehouse.FINANCING_STAGE.NO_NEED);
                    break;
            }
            companyService.addCompany(company);
        }
    }

    public Job simulatedJob(Company company){
        Random random = new Random();
        Job job = new Job();
        job.setJ_company(company);
        Position position = positionService.getPosition(random.nextInt(POSITION_COUNT) + 1);
        job.setJ_position(position);
        job.setJ_location("地址");
        job.setJ_p_require("岗位要求");
        job.setJ_remark("备注");
        job.setJ_expire("过期时间");
        job.setJ_count(0);
        job.setPosted(false);

        int temp = random.nextInt(3);
        job.setJ_gender(EnumWarehouse.GENDER.NO_REQUIREMENT);

        temp = random.nextInt(4);
        switch (temp){
            case 0:
                job.setJ_e_history(EnumWarehouse.E_HISTORY.DOCTOR);
                break;
            case 1:
                job.setJ_e_history(EnumWarehouse.E_HISTORY.MASTER);
                break;
            case 2:
                job.setJ_e_history(EnumWarehouse.E_HISTORY.BACHELOR);
                break;
            case 3:
                job.setJ_e_history(EnumWarehouse.E_HISTORY.JUNIOR_COLLEGE);
                break;
        }

        temp = random.nextInt(6);
        switch (temp){
            case 0:
                job.setJ_c_level(EnumWarehouse.C_LEVEL._985);
                break;
            case 1:
                job.setJ_c_level(EnumWarehouse.C_LEVEL._211);
                break;
            case 2:
                job.setJ_c_level(EnumWarehouse.C_LEVEL.FIRST_UNDERGRADUATE);
                break;
            case 3:
                job.setJ_c_level(EnumWarehouse.C_LEVEL.SECOND_UNDERGRADUATE);
                break;
            case 4:
                job.setJ_c_level(EnumWarehouse.C_LEVEL.JUNIOR_COLLEGE);
                break;
            case 5:
                job.setJ_c_level(EnumWarehouse.C_LEVEL.VOCATIONAL_AND_TECHNICAL_COLLEGE);
                break;
        }

        temp = random.nextInt(8);
        switch (temp){
            case 0:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.NO_REQUIREMENT);
                break;
            case 1:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.FOREIGN_EXPERIENCE);
                break;
            case 2:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.TOEFL_IELTS);
                break;
            case 3:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.TEM8);
                break;
            case 4:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.TEM4);
                break;
            case 5:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.CET6);
                break;
            case 6:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.CET4);
                break;
            case 7:
                job.setJ_e_language(EnumWarehouse.E_LANGUAGE.NONE);
                break;
        }

        temp = random.nextInt(5);
        switch (temp){
            case 0:
                job.setJ_f_language(EnumWarehouse.F_LANGUAGE.NO_REQUIREMENT);
                break;
            case 1:
                job.setJ_f_language(EnumWarehouse.F_LANGUAGE.FOREIGN_EXPERIENCE);
                break;
            case 2:
                job.setJ_f_language(EnumWarehouse.F_LANGUAGE.N2);
                break;
            case 3:
                job.setJ_f_language(EnumWarehouse.F_LANGUAGE.N1);
                break;
            case 4:
                job.setJ_f_language(EnumWarehouse.F_LANGUAGE.NONE);
                break;
        }

        temp = random.nextInt(5);
        switch (temp){
            case 0:
                job.setJ_s_range(EnumWarehouse.S_RANGE._4);
                break;
            case 1:
                job.setJ_s_range(EnumWarehouse.S_RANGE._4_6);
                break;
            case 2:
                job.setJ_s_range(EnumWarehouse.S_RANGE._6_8);
                break;
            case 3:
                job.setJ_s_range(EnumWarehouse.S_RANGE._8_10);
                break;
            case 4:
                job.setJ_s_range(EnumWarehouse.S_RANGE._10);
                break;
        }

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_if_career(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_if_career(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_if_project_experience(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_if_project_experience(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_if_fresh(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_if_fresh(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_insurance(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_insurance(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_check_up(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_check_up(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_a_bonus(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_a_bonus(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_p_leave(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_p_leave(EnumWarehouse.IF_IS_OR_NOT.NO);


        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_o_allowance(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_o_allowance(EnumWarehouse.IF_IS_OR_NOT.NO);


        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_stock(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_stock(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_t_subside(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_t_subside(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(2);
        if (temp == 0)
            job.setJ_h_subside(EnumWarehouse.IF_IS_OR_NOT.YES);
        else
            job.setJ_h_subside(EnumWarehouse.IF_IS_OR_NOT.NO);

        temp = random.nextInt(3);
        switch (temp){
            case 0:
                job.setJ_b_trip(EnumWarehouse.IF_IS_NEED_OR_NOT.NO_REQUIREMENT);
                break;
            case 1:
                job.setJ_b_trip(EnumWarehouse.IF_IS_NEED_OR_NOT.NO);
                break;
            case 2:
                job.setJ_b_trip(EnumWarehouse.IF_IS_NEED_OR_NOT.YES);
                break;
        }
        return job;
    }

    public List<Profession> simulatedProfessions(int n){
        Random random = new Random();
        List<Profession> professions = new ArrayList<>();
        for (int i = 0; i < n; i++){
            Profession profession = professionService.getProfession(random.nextInt(PROFESSION_COUNT) + 1);
            professions.add(profession);
        }
        return professions;
    }

    public void simulatedCompanyJob(){
        Random random = new Random();
        List<Company> companies = companyService.getAllCompanies();
        for (Company company : companies) {
            int temp = random.nextInt(10);
            for (int i = 0; i < temp; i++){
                Job job = simulatedDataComponent.simulatedJob(company);
                int temp1 = random.nextInt(3);
                List<Profession> professions = simulatedDataComponent.simulatedProfessions(temp1);
                JobVo jobVo = new JobVo();
                jobVo.setJob(job);
                jobVo.setProfessions(professions);
                companyService.addJob(jobVo);
                if (random.nextInt(2) == 0){
                    job.setPosted(true);
                    companyService.updateJob(job);
                    CompanyJob companyJob = new CompanyJob();
                    CompanyJobPk companyJobPk = new CompanyJobPk();
                    companyJobPk.setCj_job(job);
                    companyJobPk.setCj_company(company);
                    companyJob.setCompanyJobPk(companyJobPk);
                    companyService.addCompanyJob(companyJob);
                }
            }
        }
    }

    public void simulatedJobResume(){
        Random random = new Random();
        List<CompanyJob> companyJobs = companyService.getAllCompanyJobs();
        List<StudentResume> studentResumes = studentService.getAllStudentResumes();
        companyJobs.forEach(companyJob -> {
            int temp = random.nextInt(50);
            for (int i = 0; i < temp; i++){
                StudentResume studentResume = studentResumes.get(random.nextInt(studentResumes.size()));
                JobResume jobResume = new JobResume();
                JobResumePK jobResumePK = new JobResumePK();
                jobResumePK.setJr_job(companyJob.getCompanyJobPk().getCj_job());
                jobResumePK.setJr_resume(studentResume.getStudentResumePK().getSr_resume());
                jobResume.setJobResumePK(jobResumePK);
                jobResume.setJobToResume(true);
                jobResume.setResumeToJob(false);
                companyService.addJobResume(jobResume);
            }

        });

        studentResumes.forEach(studentResume -> {
            int temp = random.nextInt(50);
            for (int i = 0; i < temp; i++){
                CompanyJob companyJob = companyJobs.get(random.nextInt(companyJobs.size()));
                JobResume jr = companyService.getJobResumeByJobAndResume(companyJob.getCompanyJobPk().getCj_job().getJ_id(),
                        studentResume.getStudentResumePK().getSr_student().getS_id());
                if (jr == null){
                    JobResume jobResume = new JobResume();
                    JobResumePK jobResumePK = new JobResumePK();
                    jobResumePK.setJr_job(companyJob.getCompanyJobPk().getCj_job());
                    jobResumePK.setJr_resume(studentResume.getStudentResumePK().getSr_resume());
                    jobResume.setJobResumePK(jobResumePK);
                    jobResume.setJobToResume(false);
                    jobResume.setResumeToJob(true);
                    companyService.addJobResume(jobResume);
                }else {
                    jr.setResumeToJob(true);
                    companyService.updateJobResume(jr);
                }
            }
        });
    }

}
