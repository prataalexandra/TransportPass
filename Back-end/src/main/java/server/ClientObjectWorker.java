//package server;
//
//
//import domain.User;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.*;
//import java.net.Socket;
//import java.time.LocalDateTime;
//
//
//public class ClientObjectWorker implements Runnable, IObserver {
//    private IServices server;
//    private Socket connection;
//    private ObjectInputStream input;
//    private ObjectOutputStream output;
//    private volatile boolean connected;
//
//    private User currentUser;
//
//    public ClientObjectWorker(IServices server, Socket connection) {
//        this.server = server;
//        this.connection = connection;
//        try {
//            output = new ObjectOutputStream(connection.getOutputStream());
//            output.flush();
//            input = new ObjectInputStream(connection.getInputStream());
//            connected = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////
//
////    public void run() {
////        while (connected) {
////            try {
////                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////                String jsonString = reader.readLine();
////                JSONObject jsonRequest = new JSONObject(jsonString);
////
////                JSONObject response = handleRequest(jsonRequest);
////                sendResponse(response);
////            } catch (IOException e) {
////                e.printStackTrace();
////            } catch (JSONException | SrvException e) {
////                e.printStackTrace();
////            }
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
////        try {
////            input.close();
////            output.close();
////            connection.close();
////        } catch (IOException e) {
////            System.out.println("Error " + e);
////        }
////    }
//
//    public void run() {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//            while (connected) {
//                String jsonString = reader.readLine();
//                if (jsonString == null) {
//                    // Handle end of stream
//                    break;
//                }
//                JSONObject jsonRequest = new JSONObject(jsonString);
//                JSONObject response = handleRequest(jsonRequest);
//                sendResponse(response);
//
//                // Sleep for some time before processing the next request
//                Thread.sleep(1000);
//            }
//        } catch (IOException e) {
//            // Log or handle the IOException
//            e.printStackTrace();
//        } catch (JSONException | SrvException e) {
//            // Log or handle the JSONException or SrvException
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // Restore the interrupted status of the thread
//            Thread.currentThread().interrupt();
//            // Log or handle the InterruptedException
//            e.printStackTrace();
//        } finally {
//            try {
//                if (input != null) input.close();
//                if (output != null) output.close();
//                connection.close();
//            } catch (IOException e) {
//                // Log or handle the IOException
//                System.out.println("Error " + e);
//            }
//        }
//    }
//
//
//
//    private JSONObject handleRequest(JSONObject request) throws SrvException {
//        JSONObject response = new JSONObject();
//        String type = request.getString("type");
//        if (type.equals("CreateClient")) {
//            System.out.println("Create Client request...");
//            //String nume, String prenume, String email, String parola, String CNP, String statut
//            String nume = request.getString("nume");
//            String prenume = request.getString("prenume");
//            String email = request.getString("email");
//            String parola = request.getString("parola");
//            String CNP = request.getString("CNP");
//            String statut = request.getString("statut");
//            try {
//                server.createClient(nume, prenume, email, parola, CNP, statut);
//                response.put("type", "OkResponse");
//            } catch (SrvException e) {
//                response.put("type", "ErrorResponse");
//                response.put("message", e.getMessage());
//            }
//
//        }
//        if (type.equals("LoginClient")) {
//            System.out.println("Login Client request...");
//            String email = request.getString("email");
//            String parola = request.getString("parola");
//            try {
//                this.currentUser = server.login(email, parola, this);
//                response.put("type", "OkResponse");
//
//            } catch (SrvException e) {
//                connected = false;
//                response.put("type", "ErrorResponse");
//                response.put("message", e.getMessage());
//            }
//        }
//        if (type.equals("BuyTicket")) {
//            System.out.println("Buy Ticket request...");
//            LocalDateTime dataIncepere = LocalDateTime.parse(request.getString("dataIncepere"));
//            LocalDateTime dataExpirare = LocalDateTime.parse(request.getString("dataExpirare"));
//            Double pret = request.getDouble("pret");
//            String tip = request.getString("tip");
//            Long idClient = Long.valueOf(request.getString("codClient"));
//            try {
//                server.buyTicket(dataIncepere, dataExpirare, pret, tip, idClient);
//                response.put("type", "OkResponse");
//
//            } catch (SrvException e) {
//                response.put("type", "ErrorResponse");
//                response.put("message", e.getMessage());
//            }
//        }
//        /*
//        if (request instanceof LogoutRequest) {
//            System.out.println("Logout request");
//            LogoutRequest logReq = (LogoutRequest) request;
//            UserDTO udto = logReq.getUser();
//            User user = DTOUtils.getFromDTO(udto);
//            try {
//                server.logout(user, this);
//                connected = false;
//                return new OkResponse();
//
//            } catch (ChatException e) {
//                return new ErrorResponse(e.getMessage());
//            }
//        }
//        if (request instanceof SendMessageRequest) {
//            System.out.println("SendMessageRequest ...");
//            SendMessageRequest senReq = (SendMessageRequest) request;
//            MessageDTO mdto = senReq.getMessage();
//            Message message = DTOUtils.getFromDTO(mdto);
//            try {
//                server.sendMessage(message);
//                return new OkResponse();
//            } catch (ChatException e) {
//                return new ErrorResponse(e.getMessage());
//            }
//        }
//
//        if (request instanceof GetLoggedFriendsRequest) {
//            System.out.println("GetLoggedFriends Request ...");
//            GetLoggedFriendsRequest getReq = (GetLoggedFriendsRequest) request;
//            UserDTO udto = getReq.getUser();
//            User user = DTOUtils.getFromDTO(udto);
//            try {
//                User[] friends = server.getLoggedFriends(user);
//                UserDTO[] frDTO = DTOUtils.getDTO(friends);
//                return new GetLoggedFriendsResponse(frDTO);
//            } catch (ChatException e) {
//                return new ErrorResponse(e.getMessage());
//            }
//        }
//
//         */
//        return response;
//    }
//
//    private void sendResponse(JSONObject response) throws IOException {
//        System.out.println("sending response " + response.toString());
//        synchronized (output) {
//            output.writeObject(response);
//            output.flush();
//        }
//    }
//}

package server;

import domain.Abonament;
import domain.Bilet;
import domain.Client;
import domain.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.lang.System.in;

public class ClientObjectWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;
    private volatile boolean connected;

    private User currentUser;

    public ClientObjectWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        connected = true; // Assume connected until proven otherwise
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {

            while (connected) {
                String jsonString = reader.readLine();
                if (jsonString == null) {
                    System.out.println("End of stream");
                    break;
                }
                System.out.println("Received request: " + jsonString);
                JSONObject jsonRequest = new JSONObject(jsonString);
                JSONObject response = handleRequest(jsonRequest);
                writer.write(response.toString() + "\n");
                writer.flush();

                // Sleep for some time before processing the next request
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            // Log or handle the IOException
            e.printStackTrace();
        } catch (JSONException | SrvException e) {
            // Log or handle the JSONException or SrvException
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Restore the interrupted status of the thread
            Thread.currentThread().interrupt();
            // Log or handle the InterruptedException
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                // Log or handle the IOException
                System.out.println("Error " + e);
            }
        }
    }

    private JSONObject handleRequest(JSONObject request) throws SrvException {
        JSONObject response = new JSONObject();
        String type = request.getString("type");
        if (type.equals("CreateClient")) {
            System.out.println("Create Client request...");
            // String nume, String prenume, String email, String parola, String CNP, String statut
            String nume = request.getString("nume");
            String prenume = request.getString("prenume");
            String email = request.getString("email");
            String parola = request.getString("parola");
            String CNP = request.getString("CNP");
            String statut = request.getString("statut");
            try {
                if (server.alreadyExists(email,CNP)) {
                    response.put("type", "ErrorResponse");
                    response.put("message", "Client already exists.");
                } else {
                    server.createClient(nume, prenume, email, parola, CNP, statut);
                    response.put("type", "OkResponse");
                }
            } catch (SrvException e) {
                response.put("type", "ErrorResponse");
                response.put("message", e.getMessage());
            }

        } else if (type.equals("Login")) {
            System.out.println("Login request...");
            String email = request.getString("email");
            String parola = request.getString("parola");
            try {
                this.currentUser = server.login(email, parola, this);
                if (server.isClient(currentUser.getId()))
                    response.put("type", "ClientResponse");
                else response.put("type", "ControlorResponse");


            } catch (SrvException e) {
                connected = false;
                System.out.println("EROARE LA LOGIN CLIENT");
                response.put("type", "ErrorResponse");
                response.put("message", e.getMessage());
            }

        } else if (type.equals("BuyTicket")) {
            System.out.println("Buy Ticket request...");
            LocalDateTime dataIncepere = LocalDateTime.now();
            LocalDateTime dataExpirare;
            Double pret = request.getDouble("pret");
            String tip = request.getString("tip");
            if (Objects.equals(tip, "URBAN TICKET") || Objects.equals(tip, "NIGHT TICKET"))
                dataExpirare = dataIncepere.plusMinutes(60);
            else if (Objects.equals(tip, "ONE DAY TICKET"))
                dataExpirare = dataIncepere.plusHours(24);
            else
                dataExpirare = dataIncepere.plusHours(78);
            try {
                System.out.println("ID CLIENT: " + this.currentUser.getId());
                server.buyTicket(dataIncepere, dataExpirare, pret, tip, this.currentUser.getId());
                response.put("type", "OkResponse");

            } catch (SrvException e) {
                response.put("type", "ErrorResponse");
                response.put("message", e.getMessage());
            }
        } else if (type.equals("GetTickets")) {
            System.out.println("Get tickets request...");
            List<Bilet> list = server.getTicketsByClientId(this.currentUser.getId());
            response.put("size", list.size());
            for (int i = 1; i <= list.size(); i++) {
                Bilet bilet = list.get(i);
                response.put("id" + i, bilet.getId());
                response.put("dataIncepere" + i, bilet.getDataIncepere().toString());
                response.put("dataExpirare" + i, bilet.getDataExpirare().toString());
                response.put("pret" + i, bilet.getPret());
                response.put("tip" + i, bilet.getTip());
                byte[] qr = server.getQr(bilet.getId());
                response.put("qr" + i, qr);
            }
        } else if (type.equals("BuyPass")) {
            System.out.println("Buy Pass request...");
            LocalDateTime dataIncepere = LocalDateTime.now();
            LocalDateTime dataExpirare = dataIncepere.plusMonths(1);
            Double pret = request.getDouble("pret");
            String tip = request.getString("tip");

            try {
                System.out.println("ID CLIENT: " + this.currentUser.getId());
                Abonament existingAbonament = server.findAbonamentByClientId(this.currentUser.getId());
                if (existingAbonament != null) {
                    response.put("type", "ErrorResponse");
                    response.put("message", "Client already has an active pass.");
                } else {
                    server.buyPass(dataIncepere, dataExpirare, pret, tip, this.currentUser.getId());
                    response.put("type", "OkResponse");
                }

            } catch (SrvException e) {
                response.put("type", "ErrorResponse");
                response.put("message", e.getMessage());
            }
        }

     else if (type.equals("ShowPass")) {
            System.out.println("Show Pass request...");
            try {
                Abonament abonament = server.findAbonamentByClientId(this.currentUser.getId());
                if (abonament == null) {
                    System.out.println("No pass found for this client");
                    throw new SrvException("No pass found for this client");
                }
                System.out.println("ABONAMENT : " + abonament.getId() + " " + abonament.getDataIncepere() + " " + abonament.getDataExpirare() + " " + abonament.getPret() + " " + abonament.getTip());
                response.put("type", "AbonamentResponse");
                response.put("dataIncepere", abonament.getDataIncepere().toString());
                response.put("dataExpirare", abonament.getDataExpirare().toString());
                response.put("pret", abonament.getPret());
                response.put("tip", abonament.getTip());
                byte[] qr = server.getQr(abonament.getId());
                response.put("qr", qr);

            } catch (SrvException e) {
                response.put("type", "ErrorResponse");
                response.put("message", e.getMessage());
            }
        } else if (type.equals("OrarRequest")) {
            System.out.println("Orar img request...");
            String linie = request.getString("linie");
            byte[] img = server.getOrar(linie);
            response.put("type","OrarResponse");
            response.put("imagine",img);

        }
        return response;
    }
}

