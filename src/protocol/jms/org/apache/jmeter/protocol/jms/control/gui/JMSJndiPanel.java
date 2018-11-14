package org.apache.jmeter.protocol.jms.control.gui;


import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.jms.sampler.BaseJMSSampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledPasswordField;
import org.apache.jorphan.gui.JLabeledTextField;

import javax.naming.Context;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class JMSJndiPanel extends JPanel implements ChangeListener {

    public boolean getUseJNDIProperties() {
        return useProperties.isSelected();
    }

    public String getJNDInitialContextFactory() {
        return jndiICF.getText();
    }

    public String getProviderUrl() {
        return urlField.getText();
    }

    public boolean getUseAuth() {
        return useAuth.isSelected();
    }

    public String getJmsUser() {
        return jmsUser.getText();
    }

    public String getJmsPwd() {
        return jmsPwd.getText();
    }

    private final JCheckBox useProperties = new JCheckBox(JMeterUtils.getResString("jms_use_properties_file"), false); //$NON-NLS-1$
    /* Connection settings */
    private final JLabeledTextField jndiICF = new JLabeledTextField(JMeterUtils.getResString("jms_initial_context_factory")); //$NON-NLS-1$
    private final JLabeledTextField urlField = new JLabeledTextField(JMeterUtils.getResString("jms_provider_url")); //$NON-NLS-1$

    /* Authentication settings */
    private final JCheckBox useAuth = new JCheckBox(JMeterUtils.getResString("jms_use_auth"), false); //$NON-NLS-1$
    private final JLabeledTextField jmsUser = new JLabeledTextField(JMeterUtils.getResString("jms_user")); //$NON-NLS-1$
    private final JLabeledTextField jmsPwd = new JLabeledPasswordField(JMeterUtils.getResString("jms_pwd")); //$NON-NLS-1$

    /**
     * Default Constructor.
     */
    public JMSJndiPanel() {
        init();
    }

    /**
     * Clear GUI
     */
    public void clearGui() {
        useProperties.setSelected(false);
        useAuth.setSelected(false);
        jmsUser.setEnabled(false);
        jmsPwd.setEnabled(false);

        jndiICF.setText(""); // $NON-NLS-1$
        urlField.setText(""); // $NON-NLS-1$
        jmsUser.setText(""); // $NON-NLS-1$
        jmsPwd.setText(""); // $NON-NLS-1$
    }

    /**
     * Configures GUI from el
     * @param el {@link TestElement}
     */

    public void configure(TestElement el) {
        BaseJMSSampler sampler = (BaseJMSSampler) el;

        /* Set widget state */
        useProperties.setSelected(sampler.getUseJNDIPropertiesAsBoolean());
        useAuth.setSelected(sampler.isUseAuth());
        jmsUser.setEnabled(useAuth.isSelected());
        jmsPwd.setEnabled(useAuth.isSelected());

        /* Set widget value */
        jndiICF.setText(sampler.getJNDIInitialContextFactory());
        urlField.setText(sampler.getProviderUrl());
        jmsUser.setText(sampler.getUsername());
        jmsPwd.setText(sampler.getPassword());
    }

    /**
     * Shows the main properties panel for this object.
     */
    private void init() { // called from ctor, so must not be overridable
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                JMeterUtils.getResString("jms_jndi_props"))); //$NON-NLS-1$
        add(createJndiPanel(), BorderLayout.CENTER);

        useProperties.addChangeListener(this);
        useAuth.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        if (event.getSource() == useProperties) {
            final boolean isUseProperties = useProperties.isSelected();
            jndiICF.setEnabled(!isUseProperties);
            urlField.setEnabled(!isUseProperties);
            useAuth.setEnabled(!isUseProperties);
        } else if (event.getSource() == useAuth) {
            jmsUser.setEnabled(useAuth.isSelected() && useAuth.isEnabled());
            jmsPwd.setEnabled(useAuth.isSelected()  && useAuth.isEnabled());
        }
    }

    public JPanel createJndiPanel() {
        JPanel pane = new VerticalPanel();

        pane.add(useProperties);
        pane.add(jndiICF);
        pane.add(urlField);

        JPanel authpane = new HorizontalPanel();
        authpane.add(useAuth);
        authpane.add(Box.createHorizontalStrut(10));
        authpane.add(jmsUser);
        authpane.add(Box.createHorizontalStrut(10));
        authpane.add(jmsPwd);
        pane.add(authpane);

        jndiICF.setToolTipText(Context.INITIAL_CONTEXT_FACTORY);
        urlField.setToolTipText(Context.PROVIDER_URL);
        jmsUser.setToolTipText(Context.SECURITY_PRINCIPAL);
        jmsPwd.setToolTipText(Context.SECURITY_CREDENTIALS);

        return pane;
    }
}
