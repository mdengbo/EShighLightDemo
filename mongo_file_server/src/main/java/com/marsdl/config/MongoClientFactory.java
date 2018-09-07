package com.marsdl.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class MongoClientFactory {
    protected String host = "127.0.0.1";
    protected int port = 27017;
    /** auth type: */
//    protected String authenticationMechanism = "SCRAM-SHA-1";
    /** user:password@db1,user:password@db2 */
    protected String credentials = "";
    protected int connect_timeout = 3000;
    protected int socket_timeout = 3000;
    protected int connections_per_host = 100;


    public MongoClient createClient() {
        ServerAddress serverAddress = new ServerAddress(host, port);

        MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                .connectTimeout(connect_timeout)
                .socketTimeout(socket_timeout)
                .connectionsPerHost(connections_per_host)
                .build();

        // 拆分账号密码
        /*List<MongoCredential> credentialsList = new ArrayList<>();
        String[] credentialsArray = credentials.split(",");
        MongoCredential mongoCredential = null;
        switch (authenticationMechanism) {
            case "MONGODB-X509":{
                for (String temp : credentialsArray) {
                    String[] single = temp.split(":|@");
                    String user = single[0];
                    if (StringUtils.isBlank(credentials)) {
                        mongoCredential = MongoCredential.createMongoX509Credential();
                    } else {
                        mongoCredential = MongoCredential.createMongoX509Credential(user);
                    }

                    credentialsList.add(mongoCredential);
                }
                break;
            }
            case "GSSAPI":{
                for (String temp : credentialsArray) {
                    String[] single = temp.split(":|@");
                    String user = single[0];
                    mongoCredential = MongoCredential.createGSSAPICredential(user);

                    credentialsList.add(mongoCredential);
                }
                break;
            }
            case "MONGODB-CR":{
                for (String temp : credentialsArray) {
                    // user:password@db1
                    String[] single = temp.split(":|@");
                    if (single.length >= 3) {
                        String user = single[0];
                        String password = single[1];
                        String dbname = single[2];
                        mongoCredential = MongoCredential.createMongoCRCredential(user, dbname, password.toCharArray());

                        credentialsList.add(mongoCredential);
                    }
                }
                break;
            }
            case "SCRAM-SHA-1":{
                for (String temp : credentialsArray) {
                    // user:password@db1
                    String[] single = temp.split(":|@");
                    if (single.length >= 3) {
                        String user = single[0];
                        String password = single[1];
                        String dbname = single[2];
                        mongoCredential = MongoCredential.createScramSha1Credential(user, dbname, password.toCharArray());

                        credentialsList.add(mongoCredential);
                    }
                }
                break;
            }
            case "PLAIN":{
                for (String temp : credentialsArray) {
                    // user:password@db1
                    String[] single = temp.split(":|@");
                    if (single.length >= 3) {
                        String user = single[0];
                        String password = single[1];
                        String dbname = single[2];
                        mongoCredential = MongoCredential.createPlainCredential(user, dbname, password.toCharArray());

                        credentialsList.add(mongoCredential);
                    }
                }
                break;
            }
            case "":{
                for (String temp : credentialsArray) {
                    // user:password@db1
                    String[] single = temp.split(":|@");
                    if (single.length >= 3) {
                        String user = single[0];
                        String password = single[1];
                        String dbname = single[2];
                        mongoCredential = MongoCredential.createCredential(user, dbname, password.toCharArray());

                        credentialsList.add(mongoCredential);
                    }
                }
                break;
            }
        }*/


        // 创建mongo客户端
        MongoClient mongoClient = new MongoClient(serverAddress, mongoClientOptions);
       /* if (credentialsList.size() > 0) {
            mongoClient = new MongoClient(serverAddress, credentialsList, mongoClientOptions);
        } else {
            mongoClient = new MongoClient(serverAddress, mongoClientOptions);
        }*/
        return mongoClient;
    }

    /**
     * 返回
     * @return host
     */
    public String getHost() {
        return this.host;
    }

    /**
     * 设置
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 返回
     * @return port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * 设置
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 返回 auth type:
     * @return authenticationMechanism auth type:
     */
//    public String getAuthenticationMechanism() {
//        return this.authenticationMechanism;
//    }

    /**
     * 设置 auth type:
     * @param authenticationMechanism auth type:
     */
//    public void setAuthenticationMechanism(String authenticationMechanism) {
//        this.authenticationMechanism = authenticationMechanism;
//    }

    /**
     * 返回 user:password@db1user:password@db2
     * @return credentials user:password@db1user:password@db2
     */
    public String getCredentials() {
        return this.credentials;
    }

    /**
     * 设置 user:password@db1user:password@db2
     * @param credentials user:password@db1user:password@db2
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    /**
     * 返回
     * @return connect_timeout
     */
    public int getConnect_timeout() {
        return this.connect_timeout;
    }

    /**
     * 设置
     * @param connect_timeout
     */
    public void setConnect_timeout(int connect_timeout) {
        this.connect_timeout = connect_timeout;
    }

    /**
     * 返回
     * @return socket_timeout
     */
    public int getSocket_timeout() {
        return this.socket_timeout;
    }

    /**
     * 设置
     * @param socket_timeout
     */
    public void setSocket_timeout(int socket_timeout) {
        this.socket_timeout = socket_timeout;
    }

    /**
     * 返回
     * @return connections_per_host
     */
    public int getConnections_per_host() {
        return this.connections_per_host;
    }

    /**
     * 设置
     * @param connections_per_host
     */
    public void setConnections_per_host(int connections_per_host) {
        this.connections_per_host = connections_per_host;
    }
}