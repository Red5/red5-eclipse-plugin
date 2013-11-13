package org.red5.eclipse;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.project.facet.ui.AbstractFacetWizardPage;

/**
 * @author daccattato
 *
 */
public final class Red5CoreFacetInstallPage extends AbstractFacetWizardPage
{
    private Red5CoreFacetInstallConfig config;
    private Text urlPatternTextField;

    /**
     * 
     */
    public Red5CoreFacetInstallPage()
    {
        super( "red5.core.facet.install.page" );

        setTitle( "Red5 Core" );
        setDescription( "Configure the Red5 servlet." );
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl( final Composite parent )
    {
        final Composite composite = new Composite( parent, SWT.NONE );
        composite.setLayout( new GridLayout( 1, false ) );

        final Label label = new Label( composite, SWT.NONE );
        label.setLayoutData( gdhfill() );
        label.setText( "URL Pattern:" );

        this.urlPatternTextField = new Text( composite, SWT.BORDER );
        this.urlPatternTextField.setLayoutData( gdhfill() );
        this.urlPatternTextField.setText( this.config.getUrlPattern() );

        setControl( composite );
    }

    /* (non-Javadoc)
     * @see org.eclipse.wst.common.project.facet.ui.IFacetWizardPage#setConfig(java.lang.Object)
     */
    public void setConfig( final Object config )
    {
        this.config = (Red5CoreFacetInstallConfig) config;
    }

    /* (non-Javadoc)
     * @see org.eclipse.wst.common.project.facet.ui.AbstractFacetWizardPage#transferStateToConfig()
     */
    public void transferStateToConfig()
    {
        this.config.setUrlPattern( this.urlPatternTextField.getText() );
    }

    /**
     * @return
     */
    private static GridData gdhfill()
    {
        return new GridData( GridData.FILL_HORIZONTAL );
    }
}
