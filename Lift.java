import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lift {

    public static void display(Map<String, Integer> map) {

        System.out.print("Lift  :");
        for (String lift : map.keySet())
            System.out.print(" " + lift);
        System.out.println();
        System.out.print("Floor :");
        for (Integer i : map.values())
            System.out.print(" " + i+" ");
        System.out.println();

    }

    public static boolean isAccessable(int[] canAccess,int floor){
        for(int i:canAccess){
            if(i==floor)    return true;
        }
        return false;
    }
    
    public static int calculateStops(int liftPosition, int userFloor, int destinationFloor, int[] accessibleFloors) {
        List<Integer> stops = new ArrayList<>();
        stops.add(liftPosition);

        stops.add(userFloor);
        stops.add(destinationFloor);

        Collections.sort(stops);

        int stopCount = 0;
        for (int i = 1; i < 3; i++) {
            int start = stops.get(i - 1);
            int end = stops.get(i);

            for (int floor : accessibleFloors) {
                if (floor > start && floor <= end) {
                    stopCount++;
                }
            }
        }

        return stopCount;
    }

    public static String assignNearestLift(Map<String, Integer> lifts,Map<String,int[]> liftAccess, int userFloor, int destinationFloor) {
        String nearestLift = null;
        int minDistance = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : lifts.entrySet()) {
            String lift=entry.getKey();
            int liftPosition = entry.getValue();
            //int distance = Math.abs(userFloor - liftPosition);
            
            if(isAccessable(liftAccess.get(entry.getKey()), userFloor) && isAccessable(liftAccess.get(entry.getKey()), destinationFloor)){

                int stops = calculateStops(liftPosition, userFloor, destinationFloor, liftAccess.get(lift));

                if (stops < minDistance) {
                minDistance = stops;
                nearestLift = lift;
                }
            }
        }
        return nearestLift;
    }

    public static void main(String arg[]) {
        Scanner sc = new Scanner(System.in);

        Map<String, Integer> lifts = new HashMap<>();
        lifts.put("L1", 10);
        lifts.put("L2", 2);
        lifts.put("L3", 5);
        lifts.put("L4", 9);
        //lifts.put("L5", 0);
        lifts.put("L5", 1);

        Map<String, int[]> liftAccess = new HashMap<>();
        liftAccess.put("L1", new int[] { 0, 1, 2, 3, 4, 5 });
        liftAccess.put("L2", new int[] { 0, 1, 2, 3, 4, 5 });
        liftAccess.put("L3", new int[] { 0, 6, 7, 8, 9, 10 });
        liftAccess.put("L4", new int[] { 0, 6, 7, 8, 9, 10 });
       // liftAccess.put("L5", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
        liftAccess.put("L5", new int[] {});

        display(lifts);

        System.out.print("Input : ");
        int sourceFloor = sc.nextInt();
        int destinationFloor = sc.nextInt();

        String assignedLift = assignNearestLift(lifts,liftAccess, sourceFloor,destinationFloor);

        if(assignedLift!=null){
            System.out.println(assignedLift + " is assigned");

            lifts.put(assignedLift, destinationFloor);
        }else{
            System.out.println("No Lift available please try again");
        }

        System.out.println();

        display(lifts);

        sc.close();
    }
}
