package com.sales.prediction;


public class PredictionApplication {


	public static void main(String[] args) throws Exception {

		System.out.println("======================= Starting prediction =================================");
		PredictionService predictionService = new PredictionService();

		predictionService.predictPrice(2023, 336, "Linear");
		predictionService.predictPrice(2023, 336, "RandomForest");


	}

}
