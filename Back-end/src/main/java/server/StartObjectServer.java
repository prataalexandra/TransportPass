package server;


import repository.*;

import java.io.IOException;
import java.util.Properties;


public class StartObjectServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartObjectServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties " + e);
            return;
        }
        RepoUserDB repoUserDB = new RepoUserDB(serverProps);
        RepoClientDB repoClientDB = new RepoClientDB(serverProps);
        RepoBiletDB repoBiletDB = new RepoBiletDB(serverProps, repoClientDB);
        RepoAbonamentDB repoAbonamentDB = new RepoAbonamentDB(serverProps, repoClientDB);
        RepoControlorDB repoControlorDB = new RepoControlorDB(serverProps);


        IServices chatServerImpl = new ServicesImpl(repoClientDB, repoControlorDB, repoUserDB, repoAbonamentDB, repoBiletDB);


        int chatServerPort = defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong  Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + chatServerPort);
        AbstractServer server = new ChatObjectConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}