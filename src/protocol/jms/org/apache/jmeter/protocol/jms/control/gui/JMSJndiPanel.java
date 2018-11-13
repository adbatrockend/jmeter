package org.apache.jmeter.protocol.jms.control.gui;


import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.jms.sampler.BaseJMSSampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledTextField;

import javax.naming.Context;


import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
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

    private final JCheckBox useProperties = new JCheckBox(JMeterUtils.getResString("jms_use_properties_file"), false); //$NON-NLS-1$
    private final JLabeledTextField jndiICF = new JLabeledTextField(JMeterUtils.getResString("jms_initial_context_factory")); //$NON-NLS-1$
    private final JLabeledTextField urlField = new JLabeledTextField(JMeterUtils.getResString("jms_provider_url")); //$NON-NLS-1$

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
        jndiICF.setText(""); // $NON-NLS-1$
        urlField.setText(""); // $NON-NLS-1$
    }

    /**
     * Configures GUI from el
     * @param el {@link TestElement}
     */

    public void configure(TestElement el) {
        BaseJMSSampler sampler = (BaseJMSSampler) el;

        useProperties.setSelected(sampler.getUseJNDIPropertiesAsBoolean());
        jndiICF.setText(sampler.getJNDIInitialContextFactory());
        urlField.setText(sampler.getProviderUrl());
    }

    /**
     * Shows the main properties panel for this object.
     */
    private void init() { // called from ctor, so must not be overridable
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(createJndiPanel(), BorderLayout.CENTER);

        useProperties.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        if (event.getSource() == useProperties) {
            final boolean isUseProperties = useProperties.isSelected();
            jndiICF.setEnabled(!isUseProperties);
            urlField.setEnabled(!isUseProperties);
        }
    }

    public JPanel createJndiPanel() {
        JPanel pane = new VerticalPanel();

        pane.add(useProperties);
        pane.add(jndiICF);
        pane.add(urlField);


        return pane;
    }
}
