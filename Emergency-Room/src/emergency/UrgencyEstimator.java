package emergency;

import java.util.HashMap;
import java.util.Map;


/**
 * Estimates the urgency based on the patient's illness and how severe the illness is manifested.
 */
public final class UrgencyEstimator {

    private static UrgencyEstimator instance;
    private Map<Urgency, HashMap<IllnessType, Integer>> algorithm;
    private static final int MAGIC10 = 10, MAGIC20 = 20, MAGIC30 = 30, MAGIC40 = 40,
            MAGIC50 = 50, MAGIC60 = 60, MAGIC70 = 70, MAGIC80 = 80;

    private UrgencyEstimator() {
        algorithm = new HashMap<Urgency, HashMap<IllnessType, Integer>>() {
            {
                put(Urgency.IMMEDIATE,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, MAGIC60);
                                put(IllnessType.ALLERGIC_REACTION, MAGIC50);
                                put(IllnessType.BROKEN_BONES, MAGIC80);
                                put(IllnessType.BURNS, MAGIC40);
                                put(IllnessType.CAR_ACCIDENT, MAGIC30);
                                put(IllnessType.CUTS, MAGIC50);
                                put(IllnessType.FOOD_POISONING, MAGIC50);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.HEART_DISEASE, MAGIC40);
                                put(IllnessType.HIGH_FEVER, MAGIC70);
                                put(IllnessType.PNEUMONIA, MAGIC80);
                                put(IllnessType.SPORT_INJURIES, MAGIC70);
                                put(IllnessType.STROKE, 0);

                            }
                        });

                put(Urgency.URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, MAGIC40);
                                put(IllnessType.ALLERGIC_REACTION, MAGIC30);
                                put(IllnessType.BROKEN_BONES, MAGIC50);
                                put(IllnessType.BURNS, MAGIC20);
                                put(IllnessType.CAR_ACCIDENT, MAGIC20);
                                put(IllnessType.CUTS, MAGIC30);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.FOOD_POISONING, MAGIC20);
                                put(IllnessType.HEART_DISEASE, MAGIC20);
                                put(IllnessType.HIGH_FEVER, MAGIC40);
                                put(IllnessType.PNEUMONIA, MAGIC50);
                                put(IllnessType.SPORT_INJURIES, MAGIC50);
                                put(IllnessType.STROKE, 0);
                            }
                        });

                put(Urgency.LESS_URGENT,
                        new HashMap<IllnessType, Integer>() {
                            {
                                put(IllnessType.ABDOMINAL_PAIN, MAGIC10);
                                put(IllnessType.ALLERGIC_REACTION, MAGIC10);
                                put(IllnessType.BROKEN_BONES, MAGIC20);
                                put(IllnessType.BURNS, MAGIC10);
                                put(IllnessType.CAR_ACCIDENT, MAGIC10);
                                put(IllnessType.CUTS, MAGIC10);
                                put(IllnessType.FOOD_POISONING, 0);
                                put(IllnessType.HEART_ATTACK, 0);
                                put(IllnessType.HEART_DISEASE, MAGIC10);
                                put(IllnessType.HIGH_FEVER, 0);
                                put(IllnessType.PNEUMONIA, MAGIC10);
                                put(IllnessType.SPORT_INJURIES, MAGIC20);
                                put(IllnessType.STROKE, 0);
                            }
                        });

            }
        };
    }

    public static UrgencyEstimator getInstance() {
        if (instance == null) {
            instance = new UrgencyEstimator();
        }
        return instance;
    }

    //called by doctors and nurses
    public Urgency estimateUrgency(final IllnessType illnessType, final int severity) {

        if (severity >= algorithm.get(Urgency.IMMEDIATE).get(illnessType)) {
            return Urgency.IMMEDIATE;
        }
        if (severity >= algorithm.get(Urgency.URGENT).get(illnessType)) {
            return Urgency.URGENT;
        }
        if (severity >= algorithm.get(Urgency.LESS_URGENT).get(illnessType)) {
            return Urgency.LESS_URGENT;
        }
        return Urgency.NON_URGENT;
    }
}
