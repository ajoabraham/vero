/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.session.Session;

/**
 *
 * @author yulinwen
 */
public class QueryEngine {
    private Stage stage;
    
    public QueryEngine() {
        stage = new Stage();
    }
    
    public void preprocess(Session inSession) {
        stage.preprocess(inSession);
    }
}
