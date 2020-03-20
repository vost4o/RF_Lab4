package ro.usv.rf;

public class VMain {
	public static void main(String[] args) {
		double[][] learningSet = null;
		double[][] distanceMatrix = null;

		try {
			learningSet = FileUtils.readLearningSetFromFile("in.txt");
		} catch (USVInputFileCustomException e) {
			e.printStackTrace();
		}

		if (learningSet != null) {
			int numOfPatterns = learningSet.length;
			int numOfFeatures = learningSet[0].length;

			System.out.println(
					String.format("The learning set has %s patters and %s features", numOfPatterns, numOfFeatures));
			distanceMatrix = new double[numOfPatterns][];
			for (int i = 0; i < learningSet.length; i++) {
				distanceMatrix[i] = new double[numOfPatterns];
			}

			for (int i = 0; i < learningSet.length; i++) {
				for (int j = i + 1; j < learningSet.length; j++) {
					distanceMatrix[i][j] = distanceMatrix[j][i] = DistanceUtils
							.euclidianDistanceGeneralized(learningSet[i], learningSet[j]);
				}
			}

			FileUtils.writeLearningSetToFile("distanceMatrix.csv", distanceMatrix);

			for (int i = 0; i < learningSet.length; i++) {
				if (learningSet[i].length <= FileUtils.LENGTH_WITH_CLASS) {
					int pos = 0;
					double minValue = distanceMatrix[i][0];
					for (int j = 1; j < distanceMatrix[i].length; j++) {
						if (distanceMatrix[i][j] != 0) {
							if (distanceMatrix[i][j] < minValue) {
								minValue = distanceMatrix[i][j];
								pos = j;
							}
						}
					}

					System.out.println("Nearest Neighbour distance: " + minValue);
					System.out.println(i + "th Form is in " + (pos + 1) + "'s form class -> "
							+ learningSet[pos][learningSet[pos].length - 1]);
				}
			}

		}
	}
}
