package org.red5.eclipse;

import org.eclipse.wst.common.project.facet.core.IActionConfigFactory;

/**
 * @author daccattato
 *
 */
public final class Red5CoreFacetInstallConfig
{
    private String urlPattern = "*.form";

    /**
     * @return
     */
    public String getUrlPattern()
    {
        return this.urlPattern;
    }

    /**
     * @param urlPattern
     */
    public void setUrlPattern( final String urlPattern )
    {
        this.urlPattern = urlPattern;
    }

    /**
     * @author daccattato
     *
     */
    public static final class Factory implements IActionConfigFactory
    {
        /* (non-Javadoc)
         * @see org.eclipse.wst.common.project.facet.core.IActionConfigFactory#create()
         */
        public Object create()
        {
            return new Red5CoreFacetInstallConfig();
        }
    }
}
