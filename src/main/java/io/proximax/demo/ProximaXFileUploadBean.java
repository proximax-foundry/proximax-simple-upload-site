package io.proximax.demo;

import io.nem.xpx.facade.connection.RemotePeerConnection;
import io.nem.xpx.facade.upload.Upload;
import io.nem.xpx.facade.upload.UploadBinaryParameter;
import io.nem.xpx.facade.upload.UploadException;
import io.nem.xpx.facade.upload.UploadResult;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploadBean")
@SessionScoped
public class ProximaXFileUploadBean {

    private UploadedFile file;
    private String node = "https://testnet.gateway.proximax.io/";

    private List<LoadedFile> listOfFiles = new ArrayList<LoadedFile>();
    private String linkToFile;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    public void check(String hash) {
        //  check if hash is confirmed.
        final HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL("http://104.128.226.60:7890/transaction/get?hash=" +hash).openConnection();
            connection.connect();
            if(connection.getResponseCode() != 200) {
                //  do nothing
            }else {
                
                for(int i=0;i<this.listOfFiles.size();i++) {
                    if(this.listOfFiles.get(i).hash.toLowerCase().equals(hash.toLowerCase())) {
                        System.out.println(this.listOfFiles.get(i).hash);
                        this.listOfFiles.get(i).setIsConfirmed(true);
                        this.listOfFiles.get(i).setFileLink(this.listOfFiles.get(i).getNetwork()+"/xpxfs/"+hash);
                        return;
                    }
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(ProximaXFileUploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void fileUploadListener(FileUploadEvent e) throws UploadException {
        Upload upload = new Upload(new RemotePeerConnection(this.node));

        // Get uploaded file from the FileUploadEvent
        this.setFile(e.getFile());
        System.out.println("Uploaded File Name Is :: " + getFile().getFileName() + " :: Uploaded File Size :: " + getFile().getSize());

        UploadBinaryParameter parameter = UploadBinaryParameter.create()
                .senderPrivateKey("deaae199f8e511ec51eb0046cf8d78dc481e20a340d003bbfcc3a66623d09763")
                .receiverPublicKey("36e6fbc1cc5c3ef49d313721650b98d7d7d126a4f731d70071f4f3b4798cdc85")
                .data(e.getFile().getContents())
                .contentType(e.getFile().getContentType())
                .name(e.getFile().getFileName())
                .build();

        final UploadResult result = upload.uploadBinary(parameter);
        LoadedFile loadedFile = new LoadedFile();
        //loadedFile.setFileLink("https://testnet.gateway.proximax.io/xpxfs/" + result.getNemHash());
        //loadedFile.setFileLink("#confirming.....");
        loadedFile.setFileName(e.getFile().getFileName());
        loadedFile.setNetwork(this.node);
        loadedFile.setNemLink("http://104.128.226.60:7890/transaction/get?hash=" + result.getNemHash());
        loadedFile.setHash(result.getNemHash());
        this.listOfFiles.add(loadedFile);
        this.linkToFile = result.getNemHash();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                "TXN: " + result.getNemHash()));
    }

    /**
     * @return the listOfFiles
     */
    public List<LoadedFile> getListOfFiles() {
        return listOfFiles;
    }

    /**
     * @param listOfFiles the listOfFiles to set
     */
    public void setListOfFiles(List<LoadedFile> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    public class LoadedFile {

        private String fileName;
        private String fileLink;
        private String nemLink;
        private String hash;
        private String network;
        private boolean isConfirmed = false;

        /**
         * @return the fileName
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * @param fileName the fileName to set
         */
        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        /**
         * @return the fileLink
         */
        public String getFileLink() {
            return fileLink;
        }

        /**
         * @param fileLink the fileLink to set
         */
        public void setFileLink(String fileLink) {
            this.fileLink = fileLink;
        }

        /**
         * @return the nemLink
         */
        public String getNemLink() {
            return nemLink;
        }

        /**
         * @param nemLink the nemLink to set
         */
        public void setNemLink(String nemLink) {
            this.nemLink = nemLink;
        }

        /**
         * @return the hash
         */
        public String getHash() {
            return hash;
        }

        /**
         * @param hash the hash to set
         */
        public void setHash(String hash) {
            this.hash = hash;
        }

        /**
         * @return the isConfirmed
         */
        public boolean getIsConfirmed() {
            return isConfirmed;
        }

        /**
         * @param isConfirmed the isConfirmed to set
         */
        public void setIsConfirmed(boolean isConfirmed) {
            this.isConfirmed = isConfirmed;
        }

        /**
         * @return the network
         */
        public String getNetwork() {
            return network;
        }

        /**
         * @param network the network to set
         */
        public void setNetwork(String network) {
            this.network = network;
        }

    }

    /**
     * @return the linkToFile
     */
    public String getLinkToFile() {
        return linkToFile;
    }

    /**
     * @param linkToFile the linkToFile to set
     */
    public void setLinkToFile(String linkToFile) {
        this.linkToFile = linkToFile;
    }

    /**
     * @return the node
     */
    public String getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(String node) {
        this.node = node;
    }
}
