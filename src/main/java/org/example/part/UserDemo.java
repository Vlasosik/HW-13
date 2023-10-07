package org.example.part;


import java.util.List;

import static org.example.part.UserUtil.getAllCommentsAndLastPost;
import static org.example.part.UserUtil.getAllOpenTasks;


public class UserDemo {
    public static void main(String[] args) {
        //Task -1-
        User user = createDefaultUser();
        User createUser = UserUtil.createUser(user);
        System.out.println("Create user :" + "\n" + createUser);
        User updateUser = UserUtil.updateUser(user);
        System.out.println("Update user :" + "\n" + updateUser);
        User deleteUser = UserUtil.deleteUser(user);
        System.out.println("Delete user :" + "\n" + deleteUser);
        List<User> userList = UserUtil.infoUser(user);
        System.out.println("Info User List : " + "\n" + userList);
        User usersById = UserUtil.getInfoById(2L);
        System.out.println("Users by ID : " + "\n" + usersById);
        List<User> userById = UserUtil.getInfoByUserName("Samantha");
        System.out.println("User Name: " + "\n" + userById.get(0));
        //Task -2-
        getAllCommentsAndLastPost(user);
        //Task -3-
        List<UserTask> openTasks = getAllOpenTasks(user);
        if (openTasks != null) {
            System.out.println("Open Tasks for User " + user.getId() + ":");
            for (UserTask task : openTasks) {
                System.out.println("Task ID: " + task.getId());
                System.out.println("Title: " + task.getTitle());
                System.out.println("Completed: " + task.isCompleted());
                System.out.println();
            }
        } else {
            System.out.println("Failed to fetch open tasks.");
        }

    }

    public static User createDefaultUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Vlas");
        user.setUsername("kyrapika91");
        user.setEmail("vlas.potozckui@gmail.com");
        return user;
    }
}
