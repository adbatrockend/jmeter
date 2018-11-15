package org.apache.jmeter.protocol.jms.control.gui;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.protocol.jms.sampler.BaseJMSSampler;
import org.apache.jmeter.protocol.jms.sampler.JMSSampler;
import org.apache.jmeter.protocol.jms.sampler.PublisherSampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledTextField;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.BorderLayout;

// Rename this to Destination Details?
public class JMSSendDetailsPanel extends JPanel {

    public String getMessagePriority() {
        return priority.getText();
    }
    public String getExpiration() {
        return priority.getText();
    }
    public boolean getNonPersistentDelivery() { return useNonPersistentDelivery.isSelected(); }
    public String getDestination() {
        return jmsDestination.getText();
    }

    private final JLabeledTextField expiration = new JLabeledTextField(JMeterUtils.getResString("jms_expiration"),10); //$NON-NLS-1$

    private final JLabeledTextField priority = new JLabeledTextField(JMeterUtils.getResString("jms_priority"),1); //$NON-NLS-1$

    private final JCheckBox useNonPersistentDelivery = new JCheckBox(JMeterUtils.getResString("jms_use_non_persistent_delivery"),false); //$NON-NLS-1$

    private final JLabeledTextField jmsDestination = new JLabeledTextField(JMeterUtils.getResString("jms_topic")); //$NON-NLS-1$

    /**
     * Default Constructor.
     */
    public JMSSendDetailsPanel() {
        init();
    }

    /**
     * Clear GUI
     */
    public void clearGui() {
        jmsDestination.setText(""); // $NON-NLS-1$
        priority.setText(""); // $NON-NLS-1$
        expiration.setText(""); // $NON-NLS-1$
        useNonPersistentDelivery.setSelected(false);
    }

    /**
     * Configures GUI from el
     * @param el {@link TestElement}
     */
    public void configure(TestElement el) {
        if (el instanceof JMSSampler) {
            JMSSampler sampler = (JMSSampler) el;
            priority.setText(sampler.getPriority());
        } else if (el instanceof PublisherSampler) {
            PublisherSampler sampler = (PublisherSampler) el;
            priority.setText(sampler.getPriority());
            expiration.setText(sampler.getExpiration());
            useNonPersistentDelivery.setSelected(sampler.getUseNonPersistentDelivery());
            jmsDestination.setText(sampler.getDestination());
        } else {
            return;
        }
    }

    /**
     * Shows the main properties panel for this object.
     */
    private void init() { // called from ctor, so must not be overridable
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Send Details"));
                //JMeterUtils.getResString("jms_jndi_props"))); //$NON-NLS-1$
        add(createJmsSendDetailsPanel(), BorderLayout.CENTER);
    }

    public JPanel createJmsSendDetailsPanel() {
        JPanel pane = new VerticalPanel();
        JPanel destPane = new HorizontalPanel();
        destPane.add(jmsDestination/*, BorderLayout.WEST*/);
        pane.add(destPane);

        JPanel deliveryPane = new HorizontalPanel();
        deliveryPane.add(expiration);
        deliveryPane.add(priority);
        deliveryPane.add(useNonPersistentDelivery, BorderLayout.EAST);
        pane.add(deliveryPane);

        return pane;
    }
}
