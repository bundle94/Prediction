package com.sales.prediction;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class PredictionService {
    private final Instances data;
    private final Instance instance;
    //private final Classifier classifier;

    {
        try {
            //Loading the dataset file
            ConverterUtils.DataSource source = new
                    ConverterUtils.DataSource("/Users/michael/Downloads/prediction/src/main/java/com/sales/prediction/data.arff");
            data = source.getDataSet();

            // Setting the last attribute yearly sales to the class index
            data.setClassIndex(data.numAttributes() - 1);

            // Creating an instance for predictions
            instance = new DenseInstance(data.numAttributes());
            instance.setDataset(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Creating a method to predict prices
    public void predictPrice(int year, int advertisingCost, String regressor) throws Exception{
        // Initialize supplied parameters
        instance.setValue(0, year);
        instance.setValue(1, advertisingCost);

        //Creating a Linear or RandomForest regression based classifier
        Classifier classifier = regressor.equals("RandomForest") ? new RandomForest() : new LinearRegression();

        // Learning classifier with data
        classifier.buildClassifier(data);

        // Price predicting
        double predictedPrice = classifier.classifyInstance(instance);
        System.out.println("Predicted price using "+regressor+" regression:"+ predictedPrice);

        // Fetching the RMSE and MAE
        Evaluation evaluation = new Evaluation(data);
        evaluation.evaluateModel(classifier, data);
        System.out.println("Calculation MAE for "+regressor+" regression:"+ evaluation.meanAbsoluteError());
        System.out.println("Calculation RMSE rate for "+regressor+" regression:"+ evaluation.rootMeanSquaredError());
        System.out.println("=====================================================================================");

    }
}
