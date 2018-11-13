package org.apache.jmeter.protocol.jms.control.gui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.naming.Context;

import org.apache.jmeter.protocol.jms.sampler.BaseJMSSampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledPasswordField;
import org.apache.jorphan.gui.JLabeledTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles authentication properties for JMS samplers
 * @since 5.1
 */
public class JMSAuthPanel extends JPanel implements ChangeListener {
    private static final Logger log = LoggerFactory.getLogger(JMSAuthPanel.class);

    public boolean getUseAuth() {
        return useAuth.isSelected();
    }

    public String getJmsUser() {
        return jmsUser.getText();
    }

    public String getJmsPwd() {
        return jmsPwd.getText();
    }

    public void setAuthEnabled(boolean enabled) {
        useAuth.setEnabled(enabled);
    }

    private final JCheckBox useAuth = new JCheckBox(JMeterUtils.getResString("jms_use_auth"), false); //$NON-NLS-1$
    private final JLabeledTextField jmsUser = new JLabeledTextField(JMeterUtils.getResString("jms_user")); //$NON-NLS-1$
    private final JLabeledTextField jmsPwd = new JLabeledPasswordField(JMeterUtils.getResString("jms_pwd")); //$NON-NLS-1$

    /**
     * Default Constructor.
     */
    public JMSAuthPanel() {
       init();
    }

    /**
     * Clear GUI
     */
    public void clearGui() {
        jmsUser.setText(""); // $NON-NLS-1$
        jmsPwd.setText(""); // $NON-NLS-1$

        useAuth.setSelected(false);
        jmsUser.setEnabled(false);
        jmsPwd.setEnabled(false);
    }

    /**
     * Configures GUI from el
     * @param el {@link TestElement}
     */

    public void configure(TestElement el) {
        //super.configure(el);

        BaseJMSSampler sampler = (BaseJMSSampler) el;

        jmsUser.setText(sampler.getUsername());
        jmsPwd.setText(sampler.getPassword());

        useAuth.setSelected(sampler.isUseAuth());
        jmsUser.setEnabled(useAuth.isSelected());
        jmsPwd.setEnabled(useAuth.isSelected());
    }

    /**
     * Shows the main properties panel for this object.
     */
    private void init() { // called from ctor, so must not be overridable
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(createAuthPanel(), BorderLayout.CENTER);
    }

    /**
     * When a widget state changes, it will notify this class so we can
     * enable/disable the correct items.
     */
    @Override
    public void stateChanged(ChangeEvent event) {
        if (event.getSource() == useAuth) {
            jmsUser.setEnabled(useAuth.isSelected() && useAuth.isEnabled());
            jmsPwd.setEnabled(useAuth.isSelected()  && useAuth.isEnabled());
        }
    }

    public JPanel createAuthPanel() {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        pane.add(useAuth);
        pane.add(Box.createHorizontalStrut(10));
        pane.add(jmsUser);
        pane.add(Box.createHorizontalStrut(10));
        pane.add(jmsPwd);

        jmsUser.setToolTipText(Context.SECURITY_PRINCIPAL);
        jmsPwd.setToolTipText(Context.SECURITY_CREDENTIALS);

        useAuth.addChangeListener(this);

        return pane;
    }
}
