package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {
	private Map<String,Double> tarifPajak = new HashMap<>() {
		{
			put("0-50",0.05);
			put("50-250",0.1);
			put("250-500",0.15);
		}
	};

	List<Double> tarifPajakIndo = List.of(0.05,0.1,0.15);
	List<Integer> batasMinIndo = List.of(0,50,250);
	List<Integer> batasMaxIndo = List.of(50,250,500);

	List<Double> tarifPajakVietnam = List.of(0.025,0.075);
	List<Integer> batasMinVietnam = List.of(0,50);
	List<Integer> batasMaxVietnam = List.of(50,250);

	@Test
	void contextLoads() {
	}

	@Test
	void getPajak(){
		Long salaryYear = 285_000_000L;
		Double totalPajak = 0.0;
		Long temp = salaryYear/1000;
		for (Map.Entry<String, Double> entry : tarifPajak.entrySet()) {
			Integer min = Integer.parseInt(entry.getKey().split("-")[0]);
			Integer max = Integer.parseInt(entry.getKey().split("-")[1]);

			if(temp>min && temp>max){
				totalPajak += max*entry.getValue();
				temp -= max;
			} else if (temp>min && temp<max) {
				totalPajak += (temp-min)*entry.getValue();
				temp -= max;
			} else {
				if(temp>min){
					totalPajak += (temp-min)*entry.getValue();
					break;
				}
			}
		}
		System.out.println(totalPajak);
	}

	@Test
	void getPajak2(){
		Long salaryYear = 285_000_000L;
		Double totalPajak = 0.0;
		Long temp = salaryYear/1000000;
		Long tomp = salaryYear/1000000;
		for (int i = 0; i < tarifPajakIndo.size() ; i++) {
			int min = batasMinIndo.get(i);
			int max = batasMaxIndo.get(i);
			double tarif = tarifPajakIndo.get(i);

			if(min==250 && temp > min){
				totalPajak += (tomp-min)*tarif;
				break;
			}

			if(temp>min && temp>max){
				totalPajak += max*tarif;
				temp -= max;
			} else if (temp>min && temp<max) {
				totalPajak += (tomp-min)*tarif;
			}
		}
		System.out.println(totalPajak);
		Double monthly = totalPajak/12;
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.FLOOR);
		Integer monthly2 = (int) (Double.parseDouble(df.format(monthly))*1000000);

		System.out.println(monthly2);
	}

	@Test
	void getPajak3(){
		Long salaryYear = 318_000_000L;
		Double totalPajak = 0.0;
		Long temp = salaryYear/1000000;
		Long tomp = salaryYear/1000000;
		for (int i = 0; i < tarifPajakVietnam.size() ; i++) {
			DecimalFormat df = new DecimalFormat("#.##");
			int min = batasMinVietnam.get(i);
			int max = batasMaxVietnam.get(i);
			double tarif = tarifPajakVietnam.get(i);

			if(min==50 && temp > min){
				totalPajak += Double.parseDouble(df.format((tomp-min)*tarif));
				break;
			}

			if(temp>min && temp>max){
				totalPajak += Double.parseDouble(df.format(max*tarif));
				temp -= max;
			} else if (temp>min && temp<max) {
				totalPajak += Double.parseDouble(df.format((tomp-min)*tarif));
			}
			System.out.println(totalPajak);
		}
		System.out.println(totalPajak);
		Double monthly = totalPajak/12;
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.FLOOR);
		Integer monthly2 = (int) (Double.parseDouble(df.format(monthly))*1000000);

		System.out.println(monthly2);
	}
}
