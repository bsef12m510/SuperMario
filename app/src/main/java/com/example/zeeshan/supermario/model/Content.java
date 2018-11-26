package com.example.zeeshan.supermario.model;

import java.io.Serializable;

public class Content implements Serializable {

        private String rendered;
        private Boolean _protected;

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }

        public Boolean getProtected() {
            return _protected;
        }

        public void setProtected(Boolean _protected) {
            this._protected = _protected;
        }


}
