package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.entity.JobSMRBase;
import org.springframework.stereotype.Component;

@Component
public class EuclideanDistanceComponent {

    /*
    * 两个向量可以为任意维度，但必须保持维度相同，表示n维度中的两点
    * 欧式距离
    * @param vector1
    * @param vector2
    * @return 两点间距离
    */
    public double sim_distance(double[] vector1, double[] vector2) {
        double distance = 0;
        if (vector1.length == vector2.length) {
            for (int i = 0; i < vector1.length; i++) {
                double temp = Math.pow((vector1[i] - vector2[i]), 2);
                distance += temp;
            }
            distance = Math.sqrt(distance);
        }
        System.out.println(distance);
        return distance;
    }

    //向量a与矩阵的欧式距离
    public void jsim_distance(double[] vector1, double[][] vector2) {
        double[] distance = new double[vector2.length];
        if (vector1.length == vector2[0].length) {
            for (int i = 0; i < vector1.length; i++) {
                for(int j=0;j<vector2.length;j++){
                    distance[j] += Math.pow((vector1[i] - vector2[i][j]), 2);
                }  }}
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Math.sqrt(distance[i]); }
        for (double v : distance) {
            System.out.println(v);
        }
    }

    //标准化欧式距离
    public void  bzsim_distance(double[] vector1, double[][] vector2) {
        double []s=new double[vector2.length+1];
        double []avg=new double[vector2.length];
        // vector2均值
        for(int i=0;i<vector2.length;i++){
            for(int j=0;j<vector2[0].length;j++){
                avg[i]+=vector2[i][j];
            }  }
        // vector1均值
        double avg0=0;
        for (double v : vector1) {
            avg0 += v;
        }
        //vector1方差
        if (vector1.length == vector2[0].length) {
            for (double v : vector1) {
                s[0] += Math.pow(v - avg0, 2);
            }
            s[0]=Math.sqrt(s[0]/vector2.length);
        }
        //vector2方差
        for (int i = 0; i < vector2.length; i++) {
            for(int j=0;j<vector2[0].length;j++){
                s[i+1]+= Math.pow( vector2[i][j]-avg[i],2);

            }
            s[i+1]=Math.sqrt(s[i]/vector2.length);
        }

        //标准化欧氏距离
        double[] distance = new double[vector2.length];
        for (int i = 0; i < vector1.length; i++) {
            for(int j=0;j<vector2.length;j++){
                double  temp= Math.pow((vector1[i] - vector2[j][i]), 2)/s[i];
                distance[j] = distance[j]+temp;
            }  }
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Math.sqrt(distance[i]); }
        for (double v : distance) {
            System.out.println(v);
        }
    }

}
