package io.proximax.demo;

import io.nem.xpx.facade.connection.RemotePeerConnection;
import io.nem.xpx.facade.download.Download;
import io.nem.xpx.facade.download.DownloadException;
import io.nem.xpx.facade.download.DownloadParameter;
import io.nem.xpx.facade.download.DownloadResult;
import io.nem.xpx.facade.upload.Upload;
import io.nem.xpx.facade.upload.UploadBinaryParameter;
import io.nem.xpx.facade.upload.UploadException;
import io.nem.xpx.facade.upload.UploadResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alvin
 */

@ManagedBean(name = "fileDownloadWithPasswordBean")
@SessionScoped
public class ProximaXFileDownloadWithPasswordBean {
    

    private String password;
    private String hash;


    public String downloadFile() throws DownloadException,IOException {

        Download download = new Download(new RemotePeerConnection("https://testnet.gateway.proximax.io"));
        DownloadResult result = download.downloadBinary(DownloadParameter.create().nemHash(this.getHash()).securedWithPasswordPrivacyStrategy("AAAaaaaaaaAAAaaaaaaaAAAaaaaaaaAAAaaaaaaaAAAaaaaaaaAAAaaaaaaaAAAaaaaaaaAAAaaaaaaa").build());
            
        final FacesContext fc = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = fc.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType(result.getDataMessage().type());
        externalContext.setResponseHeader("Content-Disposition", "attachment;filename=" + result.getDataMessage().name());
        
        final HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        final ServletOutputStream out = response.getOutputStream();
        
        out.write(result.getData());
        out.flush();
        fc.responseComplete();
        
        return null;

    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
    
    
}