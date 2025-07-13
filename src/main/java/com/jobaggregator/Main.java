package com.jobaggregator;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import java.awt.Desktop;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Main {

    private static void styleIconButton(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(true);
                button.setBackground(new Color(200, 200, 255, 60)); // subtle hover color
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(false);
            }
        });
    }


    static boolean isDarkMode = false;


    public static void main(String[] args) {
        // ✅ Rounded corners config (Java 8 friendly)
        Map<String, Object> props = new HashMap<>();
        props.put("@button.arc", 20);
        props.put("@component.arc", 15);
        props.put("@textComponent.arc", 15);


        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("💼 AI Job Aggregator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLayout(new BorderLayout(0, 0));
            frame.setLocationRelativeTo(null);

            // Colors
            Color bgFrame = new Color(236, 239, 241);
            Color bgTop = new Color(207, 216, 220);
            Color bgResults = Color.WHITE;
            Font uiFont = new Font("Segoe UI Emoji", Font.PLAIN, 14);

            // Header
            JLabel header = new JLabel("🧠 AI JobFinder", SwingConstants.CENTER);
            header.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
            header.setBorder(new EmptyBorder(15, 0, 10, 0));

            // Input Panel
            JTextField keywordField = new JTextField(20);
            keywordField.setFont(uiFont);
            keywordField.setText("Search...");
            keywordField.setForeground(Color.GRAY);

            keywordField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (keywordField.getText().equals("Search...")) {
                        keywordField.setText("");
                        keywordField.setForeground(Color.BLACK);
                    }
                }

                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (keywordField.getText().isEmpty()) {
                        keywordField.setForeground(Color.GRAY);
                        keywordField.setText("Search...");
                    }
                }
            });


            JButton searchButton = new JButton("🔍");
            searchButton.setBorderPainted(false);
            searchButton.setContentAreaFilled(false);
            searchButton.setFocusPainted(false);
            searchButton.setOpaque(false);



            JButton themeToggle = new JButton("🌙 Toggle Theme");
            /*searchButton.setFont(uiFont);
            searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            searchButton.setFocusPainted(false);
            searchButton.setBackground(new Color(0, 123, 255));
            searchButton.setForeground(Color.WHITE);
            searchButton.setOpaque(true);
            searchButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));*/

            styleIconButton(searchButton);
            styleIconButton(themeToggle);


            // Hover effect
            searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    searchButton.setBackground(new Color(0, 105, 217));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    searchButton.setBackground(new Color(0, 123, 255));
                }
            });

            themeToggle.setFont(uiFont);

            JPanel inputGroup = new JPanel();
            inputGroup.setLayout(new BoxLayout(inputGroup, BoxLayout.X_AXIS));
            inputGroup.setBackground(bgTop);
            inputGroup.setBorder(new EmptyBorder(10, 30, 10, 30));
            inputGroup.add(new JLabel("Search: "));
            inputGroup.add(Box.createRigidArea(new Dimension(10, 0)));
            inputGroup.add(keywordField);
            inputGroup.add(Box.createRigidArea(new Dimension(10, 0)));
            inputGroup.add(searchButton);
            inputGroup.add(Box.createRigidArea(new Dimension(10, 0)));
            inputGroup.add(themeToggle);

            JPanel topPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    Color color1 = isDarkMode ? new Color(40, 48, 72) : new Color(180, 205, 255); // Deep bluish
                    Color color2 = isDarkMode ? new Color(80, 60, 110) : new Color(210, 180, 255); // Subtle violet


                    GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            };
            topPanel.setOpaque(false);
            topPanel.setLayout(new BorderLayout());
            topPanel.add(header, BorderLayout.NORTH);
            topPanel.add(inputGroup, BorderLayout.CENTER);



            // Results Panel with Gradient Background
            JTextPane resultPane = new JTextPane();
            resultPane.setContentType("text/html");
            resultPane.setEditable(false);
            resultPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
            resultPane.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            resultPane.setOpaque(false); // Make pane transparent to show gradient
            resultPane.addHyperlinkListener(e -> {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            JScrollPane scrollPane = new JScrollPane(resultPane);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            // Gradient wrapper panel
            JPanel gradientResultWrapper = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();

                    Color color1 = isDarkMode ? new Color(40, 44, 52) : new Color(99, 160, 255);
                    Color color2 = isDarkMode ? new Color(70, 60, 90) : new Color(158, 122, 255);

                    GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            };

            gradientResultWrapper.setLayout(new BorderLayout());
            gradientResultWrapper.setOpaque(false);
            gradientResultWrapper.add(scrollPane, BorderLayout.CENTER);


            // Status Bar
            JPanel statusWrapper = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    int w = getWidth();
                    int h = getHeight();

                    Color color1 = isDarkMode ? new Color(30, 30, 40) : new Color(180, 205, 255);
                    Color color2 = isDarkMode ? new Color(50, 50, 70) : new Color(210, 200, 255);

                    GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, w, h);
                }
            };
            statusWrapper.setLayout(new BorderLayout());
            statusWrapper.setPreferredSize(new Dimension(frame.getWidth(), 30));

            JLabel statusBar = new JLabel("Ready");
            statusBar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            statusBar.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
            statusBar.setBorder(new EmptyBorder(5, 10, 5, 10));
            statusWrapper.add(statusBar, BorderLayout.CENTER);


            // Add to Frame
            frame.add(gradientResultWrapper, BorderLayout.CENTER);
            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(statusWrapper, BorderLayout.SOUTH);
            ;




            // Search Button Action
            searchButton.addActionListener((ActionEvent e) -> {
                String keyword = keywordField.getText().trim();
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a keyword.");
                    return;
                }

                // Disable UI
                searchButton.setEnabled(false);
                themeToggle.setEnabled(false);

                // Spinner dialog
                JDialog loadingDialog = new JDialog(frame, "Searching...", true);
                JLabel loadingLabel = new JLabel("🔄 Searching, please wait...");
                loadingLabel.setFont(uiFont);
                loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
                loadingDialog.add(BorderLayout.CENTER, loadingLabel);
                loadingDialog.setSize(300, 100);
                loadingDialog.setLocationRelativeTo(frame);

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    List<Job> jobs;
                    final boolean darkModeNow = isDarkMode;


                    @Override
                    protected Void doInBackground() {
                        jobs = Scraper.scrapeJobs(keyword);
                        return null;
                    }

                    @Override
                    protected void done() {
                        loadingDialog.dispose();
                        StringBuilder html = new StringBuilder("<html><body style='font-family:Segoe UI; padding:10px;'>");

                        if (jobs == null || jobs.isEmpty()) {
                            html.append("<p>No jobs found.</p>");
                            statusBar.setText("No jobs found.");
                        } else {
                            for (Job job : jobs) {
                                String cardBg = darkModeNow ? "rgba(44, 44, 44, 0.75)" : "rgba(255, 255, 255, 0.85)";
                                String cardText = darkModeNow ? "#f0f0f0" : "#111111";
                                String borderColor = darkModeNow ? "#444444" : "#cccccc";

                                html.append("<div style='background:")
                                        .append(isDarkMode ? "rgba(44, 44, 44, 0.25)" : "rgba(255, 255, 255, 0.25)")
                                        .append("; color:")
                                        .append(isDarkMode ? "#ffffff" : "#000000")
                                        .append("; padding:15px; border-radius:12px; margin-bottom:15px; box-shadow:0 4px 12px rgba(0,0,0,0.08); backdrop-filter: blur(6px);'>")

                                       // .append("border: 1px solid ").append(borderColor).append(";'>")
                                        .append("<b>🏢 Company:</b> ").append(job.getCompany()).append("<br>")
                                        .append("<b>📍 Location:</b> ").append(job.getLocation()).append("<br>")
                                        .append("<b>💰 Stipend:</b> ").append(job.getStipend()).append("<br>")
                                        .append("<b>⏳ Duration:</b> ").append(job.getDuration()).append("<br>")
                                        .append("<b>🔗 <a href='").append(job.getLink()).append("' style='color:#6C63FF;text-decoration:none;'>Apply Here</a></b>")
                                        .append("</div>");
                            }


                            statusBar.setText("Found " + jobs.size() + " job(s).");
                        }

                        html.append("</body></html>");
                        resultPane.setText(html.toString());

                        // Save jobs
                        JobDAO.saveJobs(jobs);

                        searchButton.setEnabled(true);
                        themeToggle.setEnabled(true);
                    }
                };

                worker.execute();
                loadingDialog.setVisible(true);
            });

            themeToggle.addActionListener(e -> {
                try {
                    isDarkMode = !isDarkMode;

                    // Apply theme
                    UIManager.setLookAndFeel(isDarkMode ? new FlatDarkLaf() : new FlatLightLaf());
                    SwingUtilities.updateComponentTreeUI(frame);

                    // Toggle icon
                    themeToggle.setText(isDarkMode ? "☀️" : "🌙");

                    // Update dynamic colors
                    Color newTop = isDarkMode ? new Color(48, 48, 48) : new Color(207, 216, 220);
                    Color newText = isDarkMode ? Color.WHITE : Color.BLACK;
                    Color newInput = isDarkMode ? new Color(30, 30, 30) : Color.WHITE;

                    inputGroup.setBackground(newTop);
                    topPanel.setBackground(newTop);

                    resultPane.setForeground(newText);
                    resultPane.setBackground(newInput);

                    keywordField.setBackground(newInput);
                    keywordField.setForeground(newText);
                    keywordField.setCaretColor(newText);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });



            frame.setVisible(true);
        });
    }
}
