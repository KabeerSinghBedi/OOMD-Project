import java.util.*;

enum SubjectTypes {CORE_COURSE, ELECTIVE_SNA, ELECTIVE_DP, ELECTIVE_AIR, ELECTIVE_AIW}  //types of strategies

interface scoreCalculationStrategy {  //Base interface for strategies
    double calculateScore(double score);
}

class CoreCourseScoreCalculation implements scoreCalculationStrategy { //A Strategy
    @Override
    public double calculateScore(double score) {
        return score * (0.02);
    }
}
class SNACourseScoreCalculation implements scoreCalculationStrategy { //A Strategy
    @Override
    public double calculateScore(double score) {
        return score * (0.04);
    }
} 
class DPCourseScoreCalculation implements scoreCalculationStrategy { //A Strategy
    @Override
    public double calculateScore(double score) {
        return score * (0.06);
    }
}

class AIRCourseScoreCalculation implements scoreCalculationStrategy { //A Strategy
    @Override
    public double calculateScore(double score) {
        return score * (0.08);
    }
}

class AIWCourseScoreCalculation implements scoreCalculationStrategy { //A Strategy
    @Override
    public double calculateScore(double score) {
        return score * (0.10);
    }
}


class scoreCalculationStrategyFactory {  //Factory which generates objects on flies
    private final scoreCalculationStrategy CoreCourseScoreCalculationStrategy = new CoreCourseScoreCalculation();
    private final scoreCalculationStrategy SNACourseScoreCalculationStrategy = new SNACourseScoreCalculation();
    private final scoreCalculationStrategy DPCourseScoreCalculationStrategy = new DPCourseScoreCalculation();
    private final scoreCalculationStrategy AIRCourseScoreCalculationStrategy = new AIRCourseScoreCalculation();
    private final scoreCalculationStrategy AIWCourseScoreCalculationStrategy = new AIWCourseScoreCalculation();

    public scoreCalculationStrategy getscoreCalculationStrategy(SubjectTypes accountType) {
        switch (accountType) {
            case CORE_COURSE: return CoreCourseScoreCalculationStrategy;
            case ELECTIVE_SNA: return SNACourseScoreCalculationStrategy;
            case ELECTIVE_DP: return DPCourseScoreCalculationStrategy;
            case ELECTIVE_AIR: return AIRCourseScoreCalculationStrategy;
            case ELECTIVE_AIW: return AIWCourseScoreCalculationStrategy;
            default: return null;
        }
    }
}


abstract class ScoreDecorator implements scoreCalculationStrategy { //base class for decorator
   protected scoreCalculationStrategy decoratedScore;

   public ScoreDecorator(scoreCalculationStrategy decoratedScore){
      this.decoratedScore = decoratedScore;
   }

   public double calculateScore(double score){
      return decoratedScore.calculateScore(score);
   }	
}

class MinorDecorator extends ScoreDecorator { //Decorator
   scoreCalculationStrategy decoratedShape;
   public MinorDecorator(scoreCalculationStrategy decoratedShape) {
   	  super(decoratedShape);
   	  this.decoratedShape = decoratedShape;		
   }

   @Override
   public double calculateScore(double score) {
      double x = decoratedShape.calculateScore(score);	       
      addMinorCourseScore(decoratedShape);
      return x;
   }

   private void addMinorCourseScore(scoreCalculationStrategy decoratedShape){
      System.out.println("Minor Course Score Added");
   }
}

class SummerCourseDecorator extends ScoreDecorator { //Decorator
   scoreCalculationStrategy decoratedShape;
   public SummerCourseDecorator(scoreCalculationStrategy decoratedShape) {
   	  super(decoratedShape);		
   	  this.decoratedShape = decoratedShape;
   }

   @Override
   public double calculateScore(double score) {
      double x = decoratedShape.calculateScore(score);	       
      addSummerCourseScore(decoratedShape);
      return x;
   }

   private void addSummerCourseScore(scoreCalculationStrategy decoratedShape){
      System.out.println("Summer Course Score Added");
   }
}


public class Client {

    private final scoreCalculationStrategyFactory scoreCalculationStrategyFactory = new scoreCalculationStrategyFactory();
    public double calculateScore(SubjectTypes accountType, double score) {
        scoreCalculationStrategy scoreCalculationStrategyy = scoreCalculationStrategyFactory.getscoreCalculationStrategy(accountType); //Getting object from Factory
        scoreCalculationStrategy decoratedMinor = new SummerCourseDecorator(scoreCalculationStrategyy); // Decorating our object
        return decoratedMinor.calculateScore(10000);
    }

    public static void main(String[] args) {
    	SubjectTypes temp = SubjectTypes.CORE_COURSE;
    	Client x = new Client();
    	double amount = x.calculateScore(temp, 10000);
    	System.out.println(amount);
    }
}
