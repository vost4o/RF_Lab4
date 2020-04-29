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
			int pos = -1;
			double minDistance = Integer.MAX_VALUE;
			for (int i = 0; i < learningSet.length - 1; i++) {
				if(distanceMatrix[i][4] < minDistance) {
					minDistance = distanceMatrix[i][4];
					pos = i;
				}
			}
			System.out.println("Nearest Neighbour distance: " + minDistance);
			System.out.println( " 4'th Form is in " + (pos + 1) + "'s form class -> "
					+ learningSet[pos][learningSet[pos].length - 1]);

		}
	}
}
