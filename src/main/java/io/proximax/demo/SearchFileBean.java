/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.proximax.demo;

import io.nem.xpx.exceptions.ApiException;
import io.nem.xpx.facade.connection.RemotePeerConnection;
import io.nem.xpx.facade.search.Search;
import io.nem.xpx.model.ResourceHashMessageJsonEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alvin
 */

@ManagedBean(name = "searchBean")
@SessionScoped
public class SearchFileBean {
    
    private String name;
    private List<ResourceHashMessageJsonEntity> result = new ArrayList<ResourceHashMessageJsonEntity>();
    public void searchByName() {
        
        
        Search search = new Search(new RemotePeerConnection("https://testnet.gateway.proximax.io"));
        
        try {
            System.out.println(this.name);
            this.setResult(search.searchByName("deaae199f8e511ec51eb0046cf8d78dc481e20a340d003bbfcc3a66623d09763", "36e6fbc1cc5c3ef49d313721650b98d7d7d126a4f731d70071f4f3b4798cdc85", this.name));
            System.out.println(this.result.size());
        } catch (ApiException ex) {
            //Logger.getLogger(SearchFileBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            //Logger.getLogger(SearchFileBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            //Logger.getLogger(SearchFileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(this.result.size());
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the result
     */
    public List<ResourceHashMessageJsonEntity> getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(List<ResourceHashMessageJsonEntity> result) {
        this.result = result;
    }
    
    
}
