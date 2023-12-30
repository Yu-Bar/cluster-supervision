package com.ustc.client.task;
/**
 * Author:Yu-Bar
 * Date:2023/12/27-23:11
 */


import com.ustc.client.ws.SocketClient;
import com.ustc.domain.dto.ClientNodeRTM;
import com.ustc.domain.vo.ClientNodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName UpdateNodeTask
 * @Description 定时更新节点信息
 * @Author Yu-Bar
 * @Date 2023/12/27 23:11
 * @Version 1.0
 **/
@Component
@Slf4j
public class UpdateNodeTask {

    @Autowired
    private SocketClient socketClient;
    private  int cpuCores = 1;
    private Double totalSpaceInGB = 1.0;
    private int totalMemoryInMB = 1;
    public UpdateNodeTask(){
        // CPU
        try {
            // 执行系统命令
            Process process = Runtime.getRuntime().exec("wmic cpu get NumberOfCores");

            // 读取命令的输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            cpuCores = Integer.parseInt(line.trim()); // 解析输出结果得到 CPU 核数
//            System.out.println("CPU Cores: " + cpuCores);

            // 关闭流
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Disk
        File[] roots = File.listRoots();
        for (File root : roots) {
            long totalSpace = root.getTotalSpace(); // 获取每个根目录的总空间

            // 将字节转换为 GB
            totalSpaceInGB = (Double) (totalSpace * 1.0 / (1024 * 1024 * 1024));
//            System.out.println(root.getPath() + " Total Disk Space: " + totalSpaceInGB + " GB");
        }

        // Mem
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory(); // 获取 JVM 的总内存

        // 将字节转换为 MB
        totalMemoryInMB = (int) totalMemory / (1024 * 1024);
//        System.out.println("Total Memory: " + totalMemoryInMB + " MB");
    }

    /**
     * 定时更新节点信息
     */
    @Scheduled(cron = "0/3 * * * * ? ") //每 3s 触发一次
    public void updateNode() throws Exception{
        // CPU
        int cpuUsage = 0;
        // 读取命令的输出流
        Process process = Runtime.getRuntime().exec("wmic cpu get loadpercentage");
        // 执行系统命令
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));) {


            String line = reader.readLine();
            line = reader.readLine();
            line = reader.readLine();
            cpuUsage = Integer.parseInt(line.trim()); // 解析输出结果得到 CPU 使用百分比
//            System.out.println("CPU Usage: " + cpuUsage + "%");


        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Disk
        int totalDiskUsage = 0;
        // 执行系统命令
        process = Runtime.getRuntime().exec("wmic logicaldisk get FreeSpace,Size");
        // 读取命令的输出流
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));) {
            String line;
            long totalSpaceSum = 0;
            long freeSpaceSum = 0;
            line = reader.readLine();
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                // 解析输出结果，提取磁盘空间信息
                String[] diskInfo = line.trim().split("\\s+");
                if (diskInfo.length >= 2) {
                    long totalSpace = Long.parseLong(diskInfo[diskInfo.length - 1]);
                    long freeSpace = Long.parseLong(diskInfo[diskInfo.length - 2]);
                    totalSpaceSum += totalSpace;
                    freeSpaceSum += freeSpace;
                }
            }

            // 计算所有磁盘总共的占用率
            totalDiskUsage = (int) (((double) (totalSpaceSum - freeSpaceSum) * 100 / totalSpaceSum) );
            System.out.println("Total Disk Usage: " + totalDiskUsage + "%");

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Mem
        int memoryUsage = 0;
        try {
            // 获取 Runtime 实例
            Runtime runtime = Runtime.getRuntime();

            // 获取 JVM 的总内存和空闲内存
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();

            // 计算已使用内存与总内存的比例
            double usedMemory = (double) (totalMemory - freeMemory);
            memoryUsage = (int) (usedMemory * 100 / totalMemory) ;
            System.out.println("Memory Usage: " + memoryUsage + "%");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientNodeRTM clientNodeRTM = ClientNodeRTM.builder()
                .contactTime(LocalDateTime.now())
                .procNum(cpuCores)
                .capacity(totalSpaceInGB.longValue() + "GB")
                .mem(totalMemoryInMB + "MB")
                .cpuLoad(cpuUsage)
                .capacityUsage(totalDiskUsage)
                .memUsage(memoryUsage)
                .build();


        log.info("更新节点信息:{}",clientNodeRTM);
        try {
            socketClient.sendPing(clientNodeRTM);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
