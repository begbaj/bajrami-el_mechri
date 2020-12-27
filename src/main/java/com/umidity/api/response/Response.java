package com.umidity.api.response;

import com.umidity.api.Single;

/**
 * Interface for Response classes
 */
public interface Response {

    /**
     * Get a <em>Single</em> object representation of this response
     * @return
     */
    Single getSingle();
    /**
     * Get array of <em>Single</em> objects representation of this response
     * @return
     */
    Single[] getSingles();

}
