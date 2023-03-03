package frc.robot.autos;

import frc.robot.Constants;
import frc.robot.commands.IntakeCube;
import frc.robot.commands.ShootCube;
import frc.robot.subsystems.CubeSubsystem;
import frc.robot.subsystems.Swerve;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoTest extends SequentialCommandGroup {

    CubeSubsystem cubeSubsystem = new CubeSubsystem();

    public AutoTest(Swerve s_Swerve) {

        HashMap<String, Command> eventMap = new HashMap<>();
        
        List<PathPlannerTrajectory> MarkerTest1 = PathPlanner.loadPathGroup("Marker Test 1", new PathConstraints(0.5, 0.5));
        eventMap.put("Intake", new IntakeCube(cubeSubsystem, 40));
        eventMap.put("Lift Low", new ShootCube(cubeSubsystem, 10000));

        List<PathPlannerTrajectory> Auto3 = PathPlanner.loadPathGroup("Auto 3", new PathConstraints(0.25, 0.25));
        eventMap.put("Intake", new IntakeCube(cubeSubsystem, 80));
        eventMap.put("Angle Low", new InstantCommand(()-> cubeSubsystem.setCubeIntakeAngle(60)));
        eventMap.put("Shoot Low", new ShootCube(cubeSubsystem, 10000));
        eventMap.put("Angle Mid", new InstantCommand(()-> cubeSubsystem.setCubeIntakeAngle(50)));
        eventMap.put("Shoot Mid", new ShootCube(cubeSubsystem, 10000));
        eventMap.put("Angle High", new InstantCommand(()-> cubeSubsystem.setCubeIntakeAngle(40)));
        eventMap.put("Shoot High", new ShootCube(cubeSubsystem, 10000));

        {
                        // eventMap.put("Lift Low", new ShootCube(cubeSubsystem, 10000));
        
                        // eventMap.put("Shoot Cube", new PrintCommand("ARNAV IS A CHAMAN"));

                        // eventMap.put("Shoot Cube", new ShootCube(cubeSubsystem, 10000));
                        // List<PathPlannerTrajectory> pathgroup2 = PathPlanner.loadPathGroup("Hello World", new PathConstraints(2.0, 2.0));


                                // This trajectory can then be passed to a path follower such as a
                // PPSwerveControllerCommand
                // Or the path can be sampled at a given point in time for custom path following

                // Sample the state of the path at 1.2 seconds
                // PathPlannerState exampleState = (PathPlannerState) examplePath.sample(1.2);

                // Print the velocity at the sampled time
                // System.out.println(exampleState.velocityMetersPerSecond);



                        // eventMap.put("Intake", new IntakeCube(cubeSubsystem));
                // eventMap.put("Lift Low", new InstantCommand(()->cubeSubsystem.setCubeIntakeAngle(40)));

                // eventMap.put("Shoot Low", new ShootCube(cubeSubsystem, 5000));
                // eventMap.put("Intake", new IntakeCube(cubeSubsystem));
                // eventMap.put("Lift Low", new InstantCommand(()->cubeSubsystem.setCubeIntakeAngle(40)));

                // eventMap.put("Intake", new IntakeCube(cubeSubsystem, 80));
                // eventMap.put("Angle Low", new InstantCommand(()-> cubeSubsystem.setCubeIntakeAngle(60)));
                // eventMap.put("Shoot Low", new ShootCube(cubeSubsystem, 5000));
                // eventMap.put("Angle Mid", new InstantCommand(()-> cubeSubsystem.setCubeIntakeAngle(50)));
                // eventMap.put("Shoot Mid", new ShootCube(cubeSubsystem, 5000));
                // eventMap.put("Angle High", new InstantCommand(()-> cubeSubsystem.setCubeIntakeAngle(40)));
                // eventMap.put("Shoot High", new ShootCube(cubeSubsystem, 5000));

                // An example trajectory to follow. All units in meters.
        }

        SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
                s_Swerve::getPose,
                s_Swerve::resetOdometry, 
                Constants.Swerve.swerveKinematics,
                new PIDConstants(5.5, 0.0, 0.0), 
                new PIDConstants(5.8, 0.0, 0.0),
                s_Swerve::setModuleStates,
                eventMap,
                true,
                s_Swerve
        );

        Command part1 = autoBuilder.fullAuto(Auto3.get(0));
        Command part2 = autoBuilder.fullAuto(Auto3.get(1));

        addCommands(
                new InstantCommand(() -> s_Swerve.resetOdometry(Auto3.get(0).getInitialPose())), 
                part1,
                new InstantCommand(() -> s_Swerve.resetOdometry(Auto3.get(1).getInitialPose())),
                part2
        );
}
}