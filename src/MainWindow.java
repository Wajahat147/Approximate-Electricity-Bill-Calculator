import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MainWindow extends JFrame {

    // ── Colour Palette ────────────────────────────────────────────────────────
    static final Color BG_DARK      = new Color(0x0D, 0x11, 0x17);
    static final Color BG_PANEL     = new Color(0x13, 0x19, 0x22);
    static final Color BG_CARD      = new Color(0x1C, 0x24, 0x33);
    static final Color BG_CARD_SEL  = new Color(0x1E, 0x3A, 0x5F);
    static final Color ACCENT_BLUE  = new Color(0x38, 0xBD, 0xF8);
    static final Color ACCENT_GLOW  = new Color(0x0E, 0xA5, 0xE9);
    static final Color TEXT_PRIMARY = new Color(0xF1, 0xF5, 0xF9);
    static final Color TEXT_MUTED   = new Color(0x94, 0xA3, 0xB8);
    static final Color TEXT_LABEL   = new Color(0x64, 0x74, 0x8B);
    static final Color SUCCESS      = new Color(0x34, 0xD3, 0x99);
    static final Color WARNING      = new Color(0xFB, 0xBF, 0x24);
    static final Color ERROR_COLOR  = new Color(0xF8, 0x71, 0x71);
    static final Color BORDER_COLOR = new Color(0x1E, 0x2A, 0x3B);

    // ── Fonts ─────────────────────────────────────────────────────────────────
    static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 22);
    static final Font FONT_H2      = new Font("Segoe UI", Font.BOLD, 15);
    static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    static final Font FONT_MONO    = new Font("Cascadia Code", Font.BOLD, 26);
    static final Font FONT_LABEL   = new Font("Segoe UI", Font.BOLD, 12);

    // ── Appliances ────────────────────────────────────────────────────────────
    private final Appliance[] appliances = {
        new Refrigerator(),
        new Fan(),
        new Light(),
        new AirConditioner(),
        new RoomCooler(),
        new Television(),
        new Iron()
    };

    private int selectedIndex = 0;

    // ── UI Components ─────────────────────────────────────────────────────────
    private JPanel sidebarPanel;
    private ApplianceCard[] cards;
    private JLabel appNameLabel;
    private JLabel appIconLabel;
    private GlowTextField hoursField;
    private GlowTextField wattageField;
    private JLabel resultBillLabel;
    private JLabel resultKwhLabel;
    private JLabel resultSlabLabel;
    private JLabel resultExtraLabel;
    private JLabel validationLabel;
    private JPanel resultCard;
    private GlowButton calcButton;

    public MainWindow() {
        setTitle("Electricity Bill Calculator — NEPRA 2024-25");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(920, 620));
        setSize(1050, 680);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        buildUI();
        selectAppliance(0);
        setVisible(true);
    }

    // ── Build full UI ─────────────────────────────────────────────────────────
    private void buildUI() {
        setLayout(new BorderLayout(0, 0));

        // Header
        add(buildHeader(), BorderLayout.NORTH);

        // Body
        JPanel body = new JPanel(new BorderLayout(0, 0));
        body.setBackground(BG_DARK);
        body.add(buildSidebar(), BorderLayout.WEST);
        body.add(buildCalculatorPanel(), BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);
    }

    // ── Header ────────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new GradientPanel(BG_PANEL, BG_DARK, true);
        header.setLayout(new BorderLayout());
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_COLOR),
            new EmptyBorder(14, 24, 14, 24)
        ));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        JLabel bolt = new JLabel("⚡");
        bolt.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setOpaque(false);

        JLabel title = new JLabel("Electricity Bill Calculator");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_PRIMARY);

        JLabel subtitle = new JLabel("NEPRA Residential Tariff 2024–25 · Pakistan");
        subtitle.setFont(FONT_SMALL);
        subtitle.setForeground(ACCENT_BLUE);

        titleBlock.add(title);
        titleBlock.add(subtitle);
        left.add(bolt);
        left.add(titleBlock);
        header.add(left, BorderLayout.WEST);

        // Tariff badge
        RoundedLabel badge = new RoundedLabel("  PKR 27.68 – 71.00 / unit  ", 20);
        badge.setFont(FONT_SMALL);
        badge.setForeground(ACCENT_BLUE);
        badge.setBackground(new Color(0x0E, 0xA5, 0xE9, 30));
        badge.setBorder(new CompoundBorder(
            new RoundedBorder(ACCENT_GLOW, 1, 20),
            new EmptyBorder(4, 10, 4, 10)
        ));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(badge);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(BG_PANEL);
        sidebarPanel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 0, 1, BORDER_COLOR),
            new EmptyBorder(18, 12, 18, 12)
        ));
        sidebarPanel.setPreferredSize(new Dimension(210, 0));

        JLabel heading = new JLabel("APPLIANCES");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 10));
        heading.setForeground(TEXT_LABEL);
        heading.setBorder(new EmptyBorder(0, 8, 10, 0));
        sidebarPanel.add(heading);

        cards = new ApplianceCard[appliances.length];
        for (int i = 0; i < appliances.length; i++) {
            cards[i] = new ApplianceCard(appliances[i], i);
            sidebarPanel.add(cards[i]);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        }

        // Tariff info block at bottom
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(buildTariffInfo());

        return sidebarPanel;
    }

    private JPanel buildTariffInfo() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(0x0E, 0xA5, 0xE9, 20));
        p.setBorder(new CompoundBorder(
            new RoundedBorder(new Color(ACCENT_BLUE.getRed(), ACCENT_BLUE.getGreen(), ACCENT_BLUE.getBlue(), 60), 1, 10),
            new EmptyBorder(10, 12, 10, 12)
        ));

        String[] slabs = {
            "≤100 units  PKR 27.68",
            "101–200     PKR 32.47",
            "201–300     PKR 38.79",
            "301–400     PKR 45.00",
            "401–500     PKR 54.00",
            "501–600     PKR 59.00",
            "601–700     PKR 66.00",
            "700+        PKR 71.00"
        };

        JLabel head = new JLabel("NEPRA TARIFF SLABS");
        head.setFont(new Font("Segoe UI", Font.BOLD, 9));
        head.setForeground(ACCENT_BLUE);
        head.setBorder(new EmptyBorder(0, 0, 6, 0));
        p.add(head);

        for (String slab : slabs) {
            JLabel l = new JLabel(slab);
            l.setFont(new Font("Segoe Code", Font.PLAIN, 10));
            l.setForeground(TEXT_MUTED);
            p.add(l);
        }
        return p;
    }

    // ── Calculator Panel ──────────────────────────────────────────────────────
    private JPanel buildCalculatorPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG_DARK);
        wrapper.setBorder(new EmptyBorder(30, 36, 30, 36));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setOpaque(false);
        inner.setMaximumSize(new Dimension(560, Integer.MAX_VALUE));

        // Appliance heading
        JPanel headPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        headPanel.setOpaque(false);
        appIconLabel = new JLabel("⚡");
        appIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        appNameLabel = new JLabel("Appliance");
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        appNameLabel.setForeground(TEXT_PRIMARY);
        headPanel.add(appIconLabel);
        headPanel.add(appNameLabel);
        inner.add(headPanel);

        inner.add(Box.createRigidArea(new Dimension(0, 24)));

        // Input card
        JPanel inputCard = new JPanel();
        inputCard.setLayout(new BoxLayout(inputCard, BoxLayout.Y_AXIS));
        inputCard.setBackground(BG_CARD);
        inputCard.setBorder(new CompoundBorder(
            new RoundedBorder(BORDER_COLOR, 1, 14),
            new EmptyBorder(22, 24, 22, 24)
        ));

        inputCard.add(makeInputRow("\uD83D\uDD50  Daily Usage Hours", "e.g. 8",
                "Hours the appliance runs per day (1 – 24)"));
        hoursField = new GlowTextField("e.g. 8");
        styleField(hoursField);
        inputCard.add(hoursField);

        inputCard.add(Box.createRigidArea(new Dimension(0, 16)));

        inputCard.add(makeInputRow("\u26A1  Wattage (Watts)", "e.g. 1500",
                "Power rating of your appliance in Watts"));
        wattageField = new GlowTextField("e.g. 1500");
        styleField(wattageField);
        inputCard.add(wattageField);

        inner.add(inputCard);

        inner.add(Box.createRigidArea(new Dimension(0, 10)));

        // Validation label
        validationLabel = new JLabel(" ");
        validationLabel.setFont(FONT_SMALL);
        validationLabel.setForeground(ERROR_COLOR);
        validationLabel.setBorder(new EmptyBorder(2, 4, 2, 4));
        validationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inner.add(validationLabel);

        inner.add(Box.createRigidArea(new Dimension(0, 10)));

        // Calculate button
        calcButton = new GlowButton("  ⚡  Calculate Bill  ");
        calcButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calcButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        calcButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        calcButton.addActionListener(e -> calculate());
        inner.add(calcButton);

        inner.add(Box.createRigidArea(new Dimension(0, 24)));

        // Result card
        resultCard = buildResultCard();
        resultCard.setVisible(false);
        inner.add(resultCard);

        wrapper.add(inner, new GridBagConstraints());
        return wrapper;
    }

    private JLabel makeInputRow(String label, String hint, String tooltip) {
        JLabel l = new JLabel(label);
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(new EmptyBorder(0, 0, 6, 0));
        l.setToolTipText(tooltip);
        return l;
    }

    private void styleField(GlowTextField f) {
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        f.setAlignmentX(Component.LEFT_ALIGNMENT);
        f.addActionListener(e -> calculate()); // Enter key triggers calc
    }

    private JPanel buildResultCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(0x0D, 0x2B, 0x1E));
        card.setBorder(new CompoundBorder(
            new RoundedBorder(SUCCESS, 1, 14),
            new EmptyBorder(22, 24, 22, 24)
        ));

        // Header row
        JPanel rh = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        rh.setOpaque(false);
        JLabel checkIcon = new JLabel("✅");
        checkIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        JLabel resultHead = new JLabel("Monthly Bill Estimate");
        resultHead.setFont(FONT_H2);
        resultHead.setForeground(SUCCESS);
        rh.add(checkIcon);
        rh.add(resultHead);
        rh.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(rh);

        card.add(Box.createRigidArea(new Dimension(0, 14)));

        // Big PKR amount
        resultBillLabel = new JLabel("PKR 0.00");
        resultBillLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        resultBillLabel.setForeground(TEXT_PRIMARY);
        resultBillLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(resultBillLabel);

        card.add(Box.createRigidArea(new Dimension(0, 16)));

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0x34, 0xD3, 0x99, 60));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        card.add(sep);

        card.add(Box.createRigidArea(new Dimension(0, 14)));

        // Detail rows
        resultKwhLabel  = resultDetailLabel("Units Consumed", "0.00 kWh / month", "📊");
        resultSlabLabel = resultDetailLabel("Tariff Slab",    "—", "📋");
        resultExtraLabel= resultDetailLabel("Extra Info",     "—", "💡");

        card.add(resultKwhLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(resultSlabLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(resultExtraLabel);

        return card;
    }

    private JLabel resultDetailLabel(String title, String value, String emoji) {
        JLabel l = new JLabel(emoji + "  " + title + ":  " + value);
        l.setFont(FONT_BODY);
        l.setForeground(TEXT_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    // ── Selection ─────────────────────────────────────────────────────────────
    private void selectAppliance(int index) {
        if (cards != null && selectedIndex < cards.length)
            cards[selectedIndex].setSelected(false);

        selectedIndex = index;
        cards[selectedIndex].setSelected(true);

        Appliance a = appliances[index];
        appIconLabel.setText(a.getIcon());
        appNameLabel.setText(a.getName());
        hoursField.clear();
        wattageField.clear();
        validationLabel.setText(" ");
        resultCard.setVisible(false);
    }

    // ── Calculation ───────────────────────────────────────────────────────────
    private void calculate() {
        Appliance a = appliances[selectedIndex];
        String hoursText   = hoursField.getValue();
        String wattageText = wattageField.getValue();

        // Parse wattage
        double hours = 0, watts = 0;
        try { hours = Double.parseDouble(hoursText); }
        catch (NumberFormatException ex) {
            showError("⚠ Please enter a valid number for hours.");
            return;
        }
        try { watts = Double.parseDouble(wattageText); }
        catch (NumberFormatException ex) {
            showError("⚠ Please enter a valid number for wattage.");
            return;
        }

        a.setHoursUsed(hours);
        a.setWattage(watts);

        ElectricityCalculator.CalculationResult result = ElectricityCalculator.calculate(a);

        if (!result.success) {
            showError("⚠ " + result.errorMessage);
            return;
        }

        // Show results
        validationLabel.setText(" ");
        resultBillLabel.setText(String.format("PKR %,.2f", result.bill));
        resultKwhLabel.setText("📊  Units Consumed:  " + String.format("%.2f kWh / month", result.kwh));
        resultSlabLabel.setText("📋  Tariff Slab:  " + result.slabLabel);
        resultExtraLabel.setText("💡  " + a.getExtraInfo(result.bill, result.kwh));

        // Colour code by bill size
        Color billColor = result.bill < 3000 ? SUCCESS
                        : result.bill < 8000 ? WARNING
                        :                      ERROR_COLOR;
        resultBillLabel.setForeground(billColor);

        resultCard.setVisible(true);
        revalidate();
        repaint();
    }

    private void showError(String msg) {
        validationLabel.setText(msg);
        resultCard.setVisible(false);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ── Inner Classes: Custom-Painted Components ──────────────────────────────
    // ─────────────────────────────────────────────────────────────────────────

    /** Sidebar appliance card */
    class ApplianceCard extends JPanel {
        private final Appliance appliance;
        private final int index;
        private boolean selected = false;
        private boolean hovered  = false;

        ApplianceCard(Appliance appliance, int index) {
            this.appliance = appliance;
            this.index     = index;
            setOpaque(false);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
            setPreferredSize(new Dimension(186, 54));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setLayout(new BorderLayout());

            JPanel inner = new JPanel(new BorderLayout(10, 0));
            inner.setOpaque(false);
            inner.setBorder(new EmptyBorder(0, 12, 0, 12));

            JLabel icon = new JLabel(appliance.getIcon());
            icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

            JPanel text = new JPanel();
            text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
            text.setOpaque(false);
            JLabel name = new JLabel(appliance.getName());
            name.setFont(FONT_LABEL);
            name.setForeground(TEXT_PRIMARY);
            text.add(name);

            inner.add(icon, BorderLayout.WEST);
            inner.add(text, BorderLayout.CENTER);
            add(inner, BorderLayout.CENTER);

            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) { selectAppliance(index); }
                @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            });
        }

        void setSelected(boolean sel) { this.selected = sel; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            RoundRectangle2D rr = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12);
            if (selected) {
                g2.setColor(BG_CARD_SEL);
                g2.fill(rr);
                g2.setColor(ACCENT_BLUE);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(rr);
                // Left accent bar
                g2.setColor(ACCENT_BLUE);
                g2.fillRoundRect(0, 8, 3, getHeight()-16, 3, 3);
            } else if (hovered) {
                g2.setColor(BG_CARD);
                g2.fill(rr);
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Glowing text field */
    class GlowTextField extends JPanel {
        private final JTextField field;

        GlowTextField(String placeholder) {
            setLayout(new BorderLayout());
            setOpaque(false);

            field = new JTextField() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            field.setOpaque(false);
            field.setBackground(BG_CARD);
            field.setForeground(TEXT_PRIMARY);
            field.setCaretColor(ACCENT_BLUE);
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBorder(new EmptyBorder(10, 14, 10, 14));
            field.putClientProperty("placeholder", placeholder);

            // Show placeholder text
            field.addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { repaint(); }
                @Override public void focusLost(FocusEvent e)   { repaint(); }
            });

            add(field, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            boolean focused = field.hasFocus();
            if (focused) {
                // Glow shadow
                for (int i = 4; i >= 1; i--) {
                    g2.setColor(new Color(ACCENT_GLOW.getRed(), ACCENT_GLOW.getGreen(), ACCENT_GLOW.getBlue(), 18 * i));
                    g2.setStroke(new BasicStroke(i * 1.5f));
                    g2.drawRoundRect(i, i, getWidth()-i*2, getHeight()-i*2, 12, 12);
                }
                g2.setColor(ACCENT_BLUE);
            } else {
                g2.setColor(BORDER_COLOR);
            }
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            g2.setColor(BG_CARD);
            g2.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }

        void addActionListener(ActionListener l) { field.addActionListener(l); }

        String getValue() { return field.getText().trim(); }

        void clear() { field.setText(""); }
    }

    /** Glowing gradient button */
    class GlowButton extends JButton {
        private boolean hovered = false;

        GlowButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(12, 28, 12, 28));

            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                @Override public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color c1 = hovered ? new Color(0x0D, 0xB4, 0xE5) : new Color(0x08, 0x8B, 0xBB);
            Color c2 = hovered ? new Color(0x06, 0x7A, 0xA1) : new Color(0x05, 0x60, 0x7A);

            GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

            if (hovered) {
                for (int i = 3; i >= 1; i--) {
                    g2.setColor(new Color(0x38, 0xBD, 0xF8, 25 * i));
                    g2.setStroke(new BasicStroke(i * 1.5f));
                    g2.drawRoundRect(i, i, getWidth()-i*2, getHeight()-i*2, 12, 12);
                }
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Rounded label */
    class RoundedLabel extends JLabel {
        private final int radius;
        RoundedLabel(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Gradient background panel */
    static class GradientPanel extends JPanel {
        private final Color c1, c2;
        private final boolean horizontal;
        GradientPanel(Color c1, Color c2, boolean horizontal) {
            this.c1 = c1; this.c2 = c2; this.horizontal = horizontal;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(horizontal
                ? new GradientPaint(0, 0, c1, getWidth(), 0, c2)
                : new GradientPaint(0, 0, c1, 0, getHeight(), c2));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Rounded border */
    static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int   thickness, radius;
        RoundedBorder(Color color, int thickness, int radius) {
            this.color = color; this.thickness = thickness; this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, w-1, h-1, radius, radius);
            g2.dispose();
        }
        @Override
        public Insets getBorderInsets(Component c) { return new Insets(radius/2, radius/2, radius/2, radius/2); }
    }
}
