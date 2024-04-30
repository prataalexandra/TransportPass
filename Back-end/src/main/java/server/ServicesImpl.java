package server;


import domain.Client;
import domain.User;
import repository.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ServicesImpl implements IServices {

    private RepoClientDB repoClientDB;
    private RepoControlorDB repoControlorDB;
    private RepoUserDB repoUserDB;
    private RepoAbonamentDB repoAbonamentDB;
    private RepoBiletDB repoBiletDB;
    private Map<String, IObserver> loggedClients;


    public ServicesImpl(RepoClientDB repoClientDB, RepoControlorDB repoControlorDB, RepoUserDB repoUserDB, RepoAbonamentDB repoAbonamentDB, RepoBiletDB repoBiletDB) {
        this.repoClientDB = repoClientDB;
        this.repoControlorDB = repoControlorDB;
        this.repoUserDB = repoUserDB;
        this.repoAbonamentDB = repoAbonamentDB;
        this.repoBiletDB = repoBiletDB;
        this.loggedClients = new ConcurrentHashMap<>();
    }
    public synchronized void createClient(String nume, String prenume, String email, String parola, String CNP, String statut){
        Client client=new Client(1L,nume,prenume,email,parola,CNP,statut);
        repoClientDB.save(client);
    }
    public synchronized void login(String email,String parola, IObserver client) throws ChatException {
        User user=repoUserDB.findOneByUsernameAndPassword(email,parola);
        if (user!=null){
            loggedClients.put(user.getId().toString(), client);
            //notifyFriendsLoggedIn(user);
        }else
            throw new ChatException("Authentication failed.");
    }


    private final int defaultThreadsNo=5;
    /*
    private void notifyFriendsLoggedIn(User user) throws ChatException {
        Iterable<User> friends=userRepository.getFriendsOf(user);
        System.out.println("Logged "+friends);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us :friends){
            IChatObserver chatClient=loggedClients.get(us.getId());
            if (chatClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
                        chatClient.friendLoggedIn(user);
                    } catch (ChatException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }

        executor.shutdown();
    }

    private void notifyFriendsLoggedOut(User user) throws ChatException {
        Iterable<User> friends=userRepository.getFriendsOf(user);
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);


        for(User us :friends){
            IChatObserver chatClient=loggedClients.get(us.getId());
            if (chatClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ["+us.getId()+"] friend ["+user.getId()+"] logged out.");
                        chatClient.friendLoggedOut(user);
                    } catch (ChatException e) {
                        System.out.println("Error notifying friend " + e);
                    }
                });

        }
        executor.shutdown();
    }

    public synchronized void sendMessage(Message message) throws ChatException {
        String id_receiver=message.getReceiver().getId();
        IChatObserver receiverClient=loggedClients.get(id_receiver);
        if (receiverClient!=null) {      //the receiver is logged in
            messageRepository.save(message);
            receiverClient.messageReceived(message);
        }
        else
            throw new ChatException("User "+id_receiver+" not logged in.");
    }

    public synchronized void logout(User user, IChatObserver client) throws ChatException {
        IChatObserver localClient=loggedClients.remove(user.getId());
        if (localClient==null)
            throw new ChatException("User "+user.getId()+" is not logged in.");
        notifyFriendsLoggedOut(user);
    }

    public synchronized User[] getLoggedFriends(User user) throws ChatException {
       Iterable<User> friends=userRepository.getFriendsOf(user);
        Set<User> result=new TreeSet<User>();
        System.out.println("Logged friends for: "+user.getId());
        for (User friend : friends){
            if (loggedClients.containsKey(friend.getId())){    //the friend is logged in
                result.add(new User(friend.getId()));
                System.out.println("+"+friend.getId());
            }
        }
        System.out.println("Size "+result.size());
        return result.toArray(new User[result.size()]);
    }*/
}