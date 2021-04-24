package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KMeansComponent {
    @Autowired
    private EuclideanDistanceComponent euclideanDistanceComponent;
    @Autowired
    private KMeansComponent kMeansComponent;

    //初始化质心
    private List<double[]> initCentroids(List<double[]> data, int k){
        Random random = new Random();
        List<double[]> centers = new ArrayList<>();
        int[] randoms = new int[k];

        //这里不做重复值处理，因为随机数为一样的几率较小，重复值处理开销较大；
        //后期会重复做kmeans，选取代价函数最小的结果，避免这一情况
        for (int i = 0; i < k; i++) {
            randoms[i] = random.nextInt(data.size());
        }
        for (int i = 0; i < k; i++) {
            centers.add(data.get(randoms[i]));// 生成初始化中心表
        }
        return centers;
    }

    //KMeans
    private Map<String,List<double[]>> kMeansFunction(List<double[]> data, int k){
        int n = data.size();
        int length = data.get(0).length;

        //初始化数据集，倒数第二列保存该样本属于哪个簇，最后一列保存该样本跟它所属簇的误差
        List<double[]> clusterData = new ArrayList<>();
        for (double[] datum : data) {
            double[] temp = new double[length + 2];
            System.arraycopy(datum, 0, temp, 0, length);
            temp[length + 1] = 0;
            temp[length] = 0;
            clusterData.add(temp);
        }

        //决定质心是否要改变
        boolean clusterChanged = true;
        //初始化质心
        List<double[]> centers = kMeansComponent.initCentroids(data, k);

        int loopCount = 1;
        while (clusterChanged){
            loopCount ++;
            clusterChanged = false;
            for (int i = 0; i < n; i++){
                double minDistance = 1000;
                double minIndex = 0;
                for (int j = 0; j < k; j++){
                    double distance = euclideanDistanceComponent.sim_distance(data.get(i), centers.get(j));
                    if (distance < minDistance){
                        minDistance = distance;
                        clusterData.get(i)[length + 1] = minDistance;
                        minIndex = j;
                    }
                }
                if (clusterData.get(i)[length] != minIndex){
                    clusterChanged = true;
                    clusterData.get(i)[length] = minIndex;
                }
            }

            //更新质心，循环k个质心
            for (int j = 0; j < k; j++){
                double[] sum = new double[length];//储存新簇中质心的各数值和 -> 计算均值
                int count =  0;//储存新簇中元素个数 -> 计算均值
                //循环所有数据
                for (int i = 0; i < n; i++){
                    //如果该数据属于k簇
                    if (clusterData.get(i)[length] == j){
                        count ++;
                        //质心的各数值++
                        for (int l = 0; l < length; l++){
                            sum[l] += clusterData.get(i)[l];
                        }
                    }
                }
                //循环数据完毕，计算均值
                for (int l = 0; l < length; l++){
                    sum[l] /= count;
                }
                //存入质心
                centers.set(j,sum);
            }
        }
        // 输出最终质心
        for (int i = 0; i < k; i++){
            double[] centerData = centers.get(i);
            double sum = 0;
            for (double centerDatum : centerData) sum += centerDatum;
        }

        Map<String,List<double[]>> listMap = new HashMap<>();
        listMap.put("clusterData",clusterData);
        listMap.put("centersData", centers);
        return listMap;
    }

    //代价函数
    private double costFunction(List<double[]> clusterData, List<double[]> centersData){
        int k = centersData.size();//多少个簇
        int n = clusterData.size();//多少个数据
        int length = centersData.get(0).length;//数据有多少个特征
        double cost = 0;
        // 遍历数据
        for (double[] cd : clusterData){
            // 遍历数据的每一个特征
            for (int i = 0; i < length; i++){
                int cdk = (int)cd[length];//数据的倒数第二列保存该样本属于哪个簇
                //计算该数据特征与所在簇的中心点的差值平方根
                cost += Math.pow(cd[i] - centersData.get(cdk)[i],2);
            }
        }
        return cost;
    }

    //簇排名
    private int ranking(List<double[]> centersData, int ki){
        int k = centersData.size();
        int len = centersData.get(0).length;
        int rank = 1;//假设ki簇的sum最大，排名最靠前
        double kiSum = 0;
        //首先计算出ki簇的sum
        for (int i = 0; i <len; i++){
            kiSum += centersData.get(ki)[i];
        }
        //遍历centers，若有sum > kiSum，则rank++，即rank后移一位
        for (int i = 0; i < k; i++){
            double sum = 0;
            if(i != ki){
                for (int j = 0; j <len; j++){
                    sum += centersData.get(i)[j];
                }
                if (sum > kiSum) rank++;
            }
        }
        return rank;
    }

    //排名评级（三级）
    public EnumWarehouse.SUCCESS_DEGREE getDegree(int i, int n){
        if (i*1.0/n <= 1.0/3) return EnumWarehouse.SUCCESS_DEGREE.HIGH;
        else if (i*1.0/n <= 2.0/3) return EnumWarehouse.SUCCESS_DEGREE.MEDIUM;
        return EnumWarehouse.SUCCESS_DEGREE.LOW;
    }


    /*
    * 根据代价函数多次循环KMeans
    * kMeansFunction
    * @param Map<String,List<double[]>> data : clusterData,centersData
    * @param int k : 共有k个簇
    * @param int loopCount : 循环loopCount次，取最好结果
    * @return Map<String,List<double[]>> data : clusterData,centersData
    */
    public Map<String,List<double[]>> kMeansFunction(List<double[]> data, int k, int loopCount){
        //初始化
        List<double[]> finalClusterData = new ArrayList<>();
        List<double[]> finalCentersData = new ArrayList<>();
        double minCost = 9999;

        //循环loopCount次
        for (int i = 0; i < loopCount; i++){
            //获得第i次聚类结果：簇和质心
            Map<String,List<double[]>> listMap = kMeansComponent.kMeansFunction(data, k);
            List<double[]> clusterData = listMap.get("clusterData");
            List<double[]> centersData = listMap.get("centersData");
            //获得第i次聚类代价函数值
            double cost = kMeansComponent.costFunction(clusterData,centersData);
            //储存代价函数最小的结果
            if (cost < minCost){
                minCost = cost;
                finalClusterData = clusterData;
                finalCentersData = centersData;
            }
        }

        //返回循环loopCount次后cost最小的聚类结果
        Map<String,List<double[]>> listMap = new HashMap<>();
        listMap.put("clusterData", finalClusterData);
        listMap.put("centersData", finalCentersData);
        return listMap;
    }

    /*
    * 根据KMeans结果获取等级
    * getDegreeByKMeans
    * @param Map<String,List<double[]>> data : clusterData,centersData
    * @param int k : 共有k个簇
    * @param int ki : 求取第ki个簇的等级
    * @param int target : 求取clusterData中第target个数据的所处簇的等级
    * @return 两点间距离
    */
    public EnumWarehouse.SUCCESS_DEGREE getDegreeByKMeans(Map<String,List<double[]>> data,int k, int target){
        List<double[]> clusterData = data.get("clusterData");
        List<double[]> centersData = data.get("centersData");
        int len = centersData.get(0).length;
        int finalK = (int)clusterData.get(target)[len]; //获得第target数组的所属的k簇
        int rank = kMeansComponent.ranking(centersData,finalK);//计算第target数组的所属的k簇在众多k簇中的排名
        EnumWarehouse.SUCCESS_DEGREE degree = kMeansComponent.getDegree(rank, k);
        return degree;
    }
}
