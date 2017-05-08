import java.io.*; 
import java.net.*;
import java.io.Serializable;

class Files implements Serializable 
{
    private static final long serialVersionUID = 1L;

    private String destinationDirectory;
    private String sourceDirectory;
    private String filename;
    private long fileSize;
    private byte[] fileData;
    private String status;

    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
	
}



class UClient 
{    
	
	    private static String sourceFilePath = "file.txt";
	    private static String destinationPath = "Udp/";
	
	    public static Files getFileEvent() {
	        Files fileEvent = new Files();
	        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
	        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
	        fileEvent.setDestinationDirectory(destinationPath);
	        fileEvent.setFilename(fileName);
	        fileEvent.setSourceDirectory(sourceFilePath);
	        File file = new File(sourceFilePath);
	        if (file.isFile()) {
	            try {
	                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
	                long len = (int) file.length();
	                byte[] fileBytes = new byte[(int) len];
	                int read = 0;
	                int numRead = 0;
	                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
	                        fileBytes.length - read)) >= 0) {
	                    read = read + numRead;
	                }
	                fileEvent.setFileSize(len);
	                fileEvent.setFileData(fileBytes);
	                fileEvent.setStatus("Success");
	            } catch (Exception e) {
	                e.printStackTrace();
	                fileEvent.setStatus("Error");
	            }
	        } else {
	            System.out.println("path specified is not pointing to a file");
	            fileEvent.setStatus("Error");
	        }
	        return fileEvent;
	    }
	
	
	
	public static void main(String args[]) throws Exception    
      {  


    try {
	System.out.println("Client  initiating...");
	DatagramSocket clientSocket = new DatagramSocket();

    String hostName;
	InetAddress IPAddress = InetAddress.getByName("localhost");
    
    byte[] incomingData = new byte[1024];
    
    Files event = getFileEvent();
    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(outputStream);
    os.writeObject(event);
    
    byte[] data = outputStream.toByteArray();
    DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 6666); 
	
	System.out.println("Size of the file : 10000 Bytes \n");
	
    long StartTime = System.nanoTime();
    clientSocket.send(sendPacket);
    long EndTime = System.nanoTime();
    long TotalTime = EndTime - StartTime;
	
    System.out.println("File sent from client");
    DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
    clientSocket.receive(incomingPacket);
    String response = new String(incomingPacket.getData());
    
    System.out.println("Response from server:" + response.trim());
    System.out.println("Time Take for 1 Thread upload	: "+TotalTime+" nanosec");
	float speed = 10000000/TotalTime;
	System.out.println("Bandwidth for 1 Thread upload	: "+speed+" Mbps");
	
	clientSocket.close(); 
    Thread.sleep(2000);
    System.exit(0);

       } 
    catch (UnknownHostException e) {
    e.printStackTrace();}



    }
} 

