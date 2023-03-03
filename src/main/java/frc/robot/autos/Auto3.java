package frc.robot.autos;

import frc.robot.Constants;
import frc.robot.commands.IntakeAngle;
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

public class Auto3 extends SequentialCommandGroup {

    CubeSubsystem cubeSubsystem = new CubeSubsystem();

    public Auto3(Swerve s_Swerve) {

        List<PathPlannerTrajectory> Auto3 = PathPlanner.loadPathGroup("Auto 3", new PathConstraints(0.25, 0.25));
        
        HashMap<String, Command> eventMap = new HashMap<>();
        
        eventMap.put("Intake", new IntakeCube(cubeSubsystem, 30));
        eventMap.put("Angle Low", new IntakeAngle(cubeSubsystem, 80));
        eventMap.put("Shoot Low", new ShootCube(cubeSubsystem, 10000));
        eventMap.put("Angle Mid", new IntakeAngle(cubeSubsystem, 50));
        eventMap.put("Shoot Mid", new ShootCube(cubeSubsystem, 10000));
        eventMap.put("Angle High", new IntakeAngle(cubeSubsystem, 50));
        eventMap.put("Shoot High", new ShootCube(cubeSubsystem, 10000));

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