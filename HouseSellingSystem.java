// 导入所需的Java Swing和AWT包
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 房屋预售系统主类
 */
public class HouseSellingSystem {
    // UI组件
    private JFrame frame;           // 主窗口
    private JPanel mainPanel;       // 主面板
    
    // 业务数据
    private String currentUser;     // 当前登录用户
    private Map<String, User> users;      // 用户数据
    private Map<String, House> houses;    // 房屋数据
    private Map<String, List<House>> userHouses; // 用户购买的房屋
    
    // UI资源
    private ImageIcon logoIcon;     // logo图标
    
    // 主题配置
    private final ThemeConfig themeConfig = new ThemeConfig();  // 主题配置对象

    /**
     * 房屋类,存储房屋信息
     */
    static class House {
        private String id;          // 房屋编号
        private String building;    // 楼栋号
        private String unit;        // 单元号
        private String room;        // 房间号
        private double area;        // 面积
        private double price;       // 单价
        private String status;      // 状态(可售/已售)
        private String buyer;       // 购买者
        private Date saleDate;      // 销售日期
        
        /**
         * 构造函数
         */
        public House(String id, String building, String unit, String room, 
                    double area, double price) {
            this.id = id;
            this.building = building;
            this.unit = unit;
            this.room = room;
            this.area = area;
            this.price = price;
            this.status = "可售";
        }
        
        // getter和setter方法
        public String getId() { return id; }
        public String getBuilding() { return building; }
        public String getUnit() { return unit; }
        public String getRoom() { return room; }
        public double getArea() { return area; }
        public double getPrice() { return price; }
        public String getStatus() { return status; }
        public String getBuyer() { return buyer; }
        public Date getSaleDate() { return saleDate; }
        
        /**
         * 售出房屋
         */
        public void sell(String buyer) {
            this.status = "已售";
            this.buyer = buyer;
            this.saleDate = new Date();
        }
        
        // setter方法
        public void setBuilding(String building) { this.building = building; }
        public void setUnit(String unit) { this.unit = unit; }
        public void setRoom(String room) { this.room = room; }
        public void setArea(double area) { this.area = area; }
        public void setPrice(double price) { this.price = price; }
    }
    
    /**
     * 用户类,存储用户信息
     */
    static class User {
        private String username;     // 用户名
        private String password;     // 密码
        private String role;         // 角色(管理员/普通用户)
        private Date createTime;     // 创建时间
        private Date lastLoginTime;  // 最后登录时间
        
        /**
         * 构造函数
         */
        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
            this.createTime = new Date();
            this.lastLoginTime = new Date();
        }
        
        // getter方法
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getRole() { return role; }
        public Date getCreateTime() { return createTime; }
        public Date getLastLoginTime() { return lastLoginTime; }
        
        /**
         * 更新最后登录时间
         */
        public void updateLastLoginTime() {
            this.lastLoginTime = new Date();
        }
    }
    
    /**
     * 构造函数,初始化系统
     */
    public HouseSellingSystem() {
        users = new HashMap<>();
        houses = new HashMap<>();
        userHouses = new HashMap<>();
        
        // 初始化管理员账号
        users.put("admin", new User("admin", "123456", "管理员"));
        // 初始化测试数据
        addTestHouses();
        showLoginDialog();
    }
    
    /**
     * 添加测试用房屋数据
     */
    private void addTestHouses() {
        houses.put("A101", new House("A101", "A", "1", "101", 89.5, 12000));
        houses.put("A102", new House("A102", "A", "1", "102", 126.8, 13000));
        houses.put("B101", new House("B101", "B", "1", "101", 89.5, 11500));
        houses.put("B102", new House("B102", "B", "1", "102", 95.0, 12500));
        houses.put("C101", new House("C101", "C", "2", "201", 110.0, 15000));
        houses.put("C102", new House("C102", "C", "2", "202", 120.5, 16000));
        houses.put("D101", new House("D101", "D", "3", "301", 85.0, 11000));
        houses.put("D102", new House("D102", "D", "3", "302", 100.0, 14000));
        houses.put("E101", new House("E101", "E", "4", "401", 130.0, 17000));
        houses.put("E102", new House("E102", "E", "4", "402", 140.0, 18000));
        houses.put("F101", new House("F101", "F", "5", "501", 150.0, 20000));
        houses.put("F102", new House("F102", "F", "5", "502", 160.0, 21000));
        houses.put("G101", new House("G101", "G", "6", "601", 170.0, 22000));
        houses.put("G102", new House("G102", "G", "6", "602", 180.0, 23000));
        houses.put("H101", new House("H101", "H", "7", "701", 190.0, 24000));
        houses.put("H102", new House("H102", "H", "7", "702", 200.0, 25000));
        houses.put("I101", new House("I101", "I", "8", "801", 210.0, 26000));
        houses.put("I102", new House("I102", "I", "8", "802", 220.0, 27000));
        houses.put("J101", new House("J101", "J", "9", "901", 230.0, 28000));
        houses.put("J102", new House("J102", "J", "9", "902", 240.0, 29000));
    }
    
    /**
     * 显示登录对话框
     */
    private void showLoginDialog() {
        JDialog loginDialog = new JDialog((Frame)null, "房屋预售系统", true);
        loginDialog.setLayout(new GridBagLayout());
        loginDialog.getContentPane().setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30); // 增加组件间距
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH; // 组件填充方式改为BOTH以支持动态调整
        
        // 标题
        JLabel titleLabel = new JLabel("用户登录", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
        titleLabel.setForeground(themeConfig.themeColor);
        
        // 输入框
        JTextField usernameField = new JTextField(30);
        JPasswordField passwordField = new JPasswordField(30);
        Font inputFont = new Font("微软雅黑", Font.PLAIN, 32);
        usernameField.setFont(inputFont);
        passwordField.setFont(inputFont);
        
        // 标签字体
        Font labelFont = new Font("微软雅黑", Font.BOLD, 32);
        JLabel usernameLabel = new JLabel("用户名:");
        JLabel passwordLabel = new JLabel("密码:");
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        
        // 按钮
        JButton loginButton = createGradientButton("登录");
        JButton registerButton = createGradientButton("注册");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
        registerButton.setFont(new Font("微软雅黑", Font.BOLD, 24));
        
        // 设置输入框和按钮的首选大小
        Dimension fieldSize = new Dimension(400, 60); // 增加高度
        Dimension buttonSize = new Dimension(200, 60);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        
        // 布局
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginDialog.add(titleLabel, gbc);
        
        gbc.gridy = 1; gbc.gridwidth = 1;
        loginDialog.add(usernameLabel, gbc);
        gbc.gridx = 1;
        loginDialog.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        loginDialog.add(passwordLabel, gbc);
        gbc.gridx = 1;
        loginDialog.add(passwordField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(60)); // 增加按钮间距
        buttonPanel.add(registerButton);
        loginDialog.add(buttonPanel, gbc);
        
        // 登录按钮事件处理
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            
            User user = users.get(username);
            if(user != null && user.getPassword().equals(password)) {
                currentUser = username;
                user.updateLastLoginTime();
                loginDialog.dispose();
                createMainGUI();
            } else {
                JOptionPane.showMessageDialog(loginDialog, "用户名或密码错误!", "错误", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // 注册按钮事件处理
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            
            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginDialog, "用户名和密码不能为空!", 
                    "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(users.containsKey(username)) {
                JOptionPane.showMessageDialog(loginDialog, "用户名已存在!", 
                    "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            users.put(username, new User(username, password, "普通用户"));
            JOptionPane.showMessageDialog(loginDialog, "注册成功!", 
                "成功", JOptionPane.INFORMATION_MESSAGE);
        });
        
        loginDialog.setSize(1600, 800); // 增大对话框尺寸
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setVisible(true);
    }
    
    /**
     * 创建渐变按钮
     */
    private JButton createGradientButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                  RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = new GradientPaint(0, 0, themeConfig.themeColor,
                                                    0, getHeight(), 
                                                    themeConfig.themeColor.darker());
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 增加圆角
                
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), 
                            (getWidth() - g2.getFontMetrics().stringWidth(getText())) / 2,
                            (getHeight() + g2.getFontMetrics().getAscent()) / 2);
                g2.dispose();
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        
        // 添加鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(themeConfig.themeColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(themeConfig.themeColor);
            }
        });
        
        return button;
    }
    
    /**
     * 显示统计报表对话框
     */
    private void showStatsDialog() {
        JDialog dialog = new JDialog(frame, "统计报表", true);
        dialog.setLayout(new BorderLayout(20, 20)); // 增加组件间距
        
        Font font = new Font("微软雅黑", Font.BOLD, 32);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
        tabbedPane.setBackground(themeConfig.bgColor);
        
        // 销售统计面板
        JPanel salesPanel = createStatsPanel();
        int soldCount = 0;
        double totalSales = 0;
        for(House h : houses.values()) {
            if("已售".equals(h.getStatus())) {
                soldCount++;
                totalSales += h.getArea() * h.getPrice();
            }
        }
        
        JTextArea salesStats = new JTextArea();
        salesStats.setFont(font);
        salesStats.setBackground(themeConfig.bgColor);
        salesStats.setForeground(themeConfig.textColor);
        salesStats.append("已售房屋数量: " + soldCount + "\n");
        salesStats.append("销售总额: " + String.format("%.2f", totalSales) + "元\n");
        salesPanel.add(new JScrollPane(salesStats), BorderLayout.CENTER);
        
        // 房屋状态统计面板
        JPanel statusPanel = createStatsPanel();
        int availableCount = 0;
        for(House h : houses.values()) {
            if("可售".equals(h.getStatus())) {
                availableCount++;
            }
        }
        
        JTextArea statusStats = new JTextArea();
        statusStats.setFont(font);
        statusStats.setBackground(themeConfig.bgColor);
        statusStats.setForeground(themeConfig.textColor);
        statusStats.append("可售房屋数量: " + availableCount + "\n");
        statusStats.append("已售房屋数量: " + soldCount + "\n");
        statusStats.append("总房屋数量: " + houses.size() + "\n");
        statusPanel.add(new JScrollPane(statusStats), BorderLayout.CENTER);
        
        tabbedPane.addTab("销售统计", salesPanel);
        tabbedPane.addTab("房屋状态", statusPanel);
        
        dialog.add(tabbedPane, BorderLayout.CENTER);
        dialog.setSize(1600, 1000);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    /**
     * 创建统计面板的辅助方法
     */
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(themeConfig.bgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // 增加边距
        return panel;
    }
    
    /**
     * 显示用户管理对话框
     */
    private void showUserManageDialog() {
        JDialog dialog = new JDialog(frame, "用户管理", true);
        dialog.setLayout(new BorderLayout(20, 20)); // 增加组件间距
        
        Font font = new Font("微软雅黑", Font.BOLD, 24);
        
        // 用户列表
        String[] columnNames = {"用户名", "角色", "创建时间", "最后登录"};
        Object[][] data = new Object[users.size()][4];
        int i = 0;
        for(User user : users.values()) {
            data[i] = new Object[]{
                user.getUsername(),
                user.getRole(),
                user.getCreateTime(),
                user.getLastLoginTime()
            };
            i++;
        }
        
        JTable table = new JTable(data, columnNames);
        table.setFont(font);
        table.setRowHeight(60); // 增加行高
        table.setBackground(themeConfig.bgColor);
        table.setForeground(themeConfig.textColor);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setBackground(themeConfig.themeColor);
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(themeConfig.bgColor);
        scrollPane.setBorder(BorderFactory.createLineBorder(themeConfig.themeColor, 2));
        
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(1600, 1000);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    /**
     * 显示房屋管理对话框
     */
    private void showHouseManageDialog() {
        JDialog dialog = new JDialog(frame, "房屋信息管理", true);
        dialog.setLayout(new BorderLayout(20, 20)); // 增加组件间距
        
        Font font = new Font("微软雅黑", Font.BOLD, 24);
        
        // 房屋列表
        String[] columnNames = {"编号", "楼栋", "单元", "房间号", "面积", "单价", "状态", "购买者", "销售日期"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for(House h : houses.values()) {
            model.addRow(new Object[]{
                h.getId(), h.getBuilding(), h.getUnit(), h.getRoom(),
                h.getArea(), h.getPrice(), h.getStatus(),
                h.getBuyer(), h.getSaleDate()
            });
        }
        
        JTable table = new JTable(model);
        table.setFont(font);
        table.setRowHeight(50);
        table.setBackground(themeConfig.bgColor);
        table.setForeground(themeConfig.textColor);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setBackground(themeConfig.themeColor);
        table.getTableHeader().setForeground(Color.WHITE);
        
        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(themeConfig.bgColor);
        
        JButton addButton = createGradientButton("添加房屋");
        JButton editButton = createGradientButton("编辑房屋");
        JButton deleteButton = createGradientButton("删除房屋");
        
        addButton.setFont(font);
        editButton.setFont(font);
        deleteButton.setFont(font);
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        // 添加按钮事件
        addButton.addActionListener(e -> {
            showAddHouseDialog(dialog, model);
        });
        
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow < 0) {
                JOptionPane.showMessageDialog(dialog, "请先选择要编辑的房屋!");
                return;
            }
            
            String id = (String)table.getValueAt(selectedRow, 0);
            House house = houses.get(id);
            
            JDialog editDialog = new JDialog(dialog, "编辑房屋", true);
            editDialog.setLayout(new GridLayout(7, 2, 10, 10));
            
            JTextField buildingField = new JTextField(house.getBuilding());
            JTextField unitField = new JTextField(house.getUnit());
            JTextField roomField = new JTextField(house.getRoom());
            JTextField areaField = new JTextField(String.valueOf(house.getArea()));
            JTextField priceField = new JTextField(String.valueOf(house.getPrice()));
            
            JButton confirmButton = createGradientButton("确认");
            JButton cancelButton = createGradientButton("取消");
            
            buildingField.setFont(font);
            unitField.setFont(font);
            roomField.setFont(font);
            areaField.setFont(font);
            priceField.setFont(font);
            confirmButton.setFont(font);
            cancelButton.setFont(font);
            
            confirmButton.addActionListener(ev -> {
                try {
                    house.setBuilding(buildingField.getText());
                    house.setUnit(unitField.getText());
                    house.setRoom(roomField.getText());
                    house.setArea(Double.parseDouble(areaField.getText()));
                    house.setPrice(Double.parseDouble(priceField.getText()));
                    
                    model.setValueAt(house.getBuilding(), selectedRow, 1);
                    model.setValueAt(house.getUnit(), selectedRow, 2);
                    model.setValueAt(house.getRoom(), selectedRow, 3);
                    model.setValueAt(house.getArea(), selectedRow, 4);
                    model.setValueAt(house.getPrice(), selectedRow, 5);
                    
                    editDialog.dispose();
                    JOptionPane.showMessageDialog(dialog, "修改成功!");
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editDialog, "请输入有效的数字!");
                }
            });
            
            cancelButton.addActionListener(ev -> editDialog.dispose());
            
            addLabelAndField(editDialog, "房屋编号: " + id, new JTextField(), font);
            addLabelAndField(editDialog, "楼栋号:", buildingField, font);
            addLabelAndField(editDialog, "单元号:", unitField, font);
            addLabelAndField(editDialog, "房间号:", roomField, font);
            addLabelAndField(editDialog, "面积:", areaField, font);
            addLabelAndField(editDialog, "单价:", priceField, font);
            
            editDialog.add(confirmButton);
            editDialog.add(cancelButton);
            
            editDialog.setSize(800, 600);
            editDialog.setLocationRelativeTo(dialog);
            editDialog.setVisible(true);
        });
        
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow < 0) {
                JOptionPane.showMessageDialog(dialog, "请先选择要删除的房屋!");
                return;
            }
            
            String id = (String)table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(dialog, 
                "确定要删除房屋 " + id + " 吗?", "确认删除",
                JOptionPane.YES_NO_OPTION);
                
            if(confirm == JOptionPane.YES_OPTION) {
                houses.remove(id);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(dialog, "删除成功!");
            }
        });
        
        dialog.add(buttonPanel, BorderLayout.NORTH);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        
        dialog.setSize(1600, 1000);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    /**
     * 显示添加房屋对话框
     */
    private void showAddHouseDialog(JDialog parent, DefaultTableModel model) {
        JDialog dialog = new JDialog(parent, "添加新房屋", true);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));
        dialog.getContentPane().setBackground(themeConfig.bgColor);
        
        Font font = new Font("微软雅黑", Font.BOLD, 24);
        
        JTextField idField = createStyledTextField();
        JTextField buildingField = createStyledTextField();
        JTextField unitField = createStyledTextField();
        JTextField roomField = createStyledTextField();
        JTextField areaField = createStyledTextField();
        JTextField priceField = createStyledTextField();
        
        JButton confirmButton = createGradientButton("确认");
        JButton cancelButton = createGradientButton("取消");
        
        // 设置字体
        idField.setFont(font);
        buildingField.setFont(font);
        unitField.setFont(font);
        roomField.setFont(font);
        areaField.setFont(font);
        priceField.setFont(font);
        confirmButton.setFont(font);
        cancelButton.setFont(font);
        
        // 添加标签和输入框
        addLabelAndField(dialog, "房屋编号:", idField, font);
        addLabelAndField(dialog, "楼栋号:", buildingField, font);
        addLabelAndField(dialog, "单元号:", unitField, font);
        addLabelAndField(dialog, "房间号:", roomField, font);
        addLabelAndField(dialog, "面积:", areaField, font);
        addLabelAndField(dialog, "单价:", priceField, font);
        
        // 添加按钮
        dialog.add(confirmButton);
        dialog.add(cancelButton);
        
        // 按钮事件
        confirmButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                if(houses.containsKey(id)) {
                    JOptionPane.showMessageDialog(dialog, "房屋编号已存在!");
                    return;
                }
                
                House newHouse = new House(
                    id,
                    buildingField.getText(),
                    unitField.getText(),
                    roomField.getText(),
                    Double.parseDouble(areaField.getText()),
                    Double.parseDouble(priceField.getText())
                );
                
                houses.put(id, newHouse);
                model.addRow(new Object[]{
                    newHouse.getId(), newHouse.getBuilding(),
                    newHouse.getUnit(), newHouse.getRoom(),
                    newHouse.getArea(), newHouse.getPrice(),
                    newHouse.getStatus(), null, null
                });
                
                dialog.dispose();
                JOptionPane.showMessageDialog(parent, "添加成功!");
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "请输入有效的数字!");
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
    
    /**
     * 创建样式化的文本框
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(themeConfig.themeColor, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(Color.WHITE);
        field.setForeground(themeConfig.textColor);
        return field;
    }
    
    /**
     * 添加标签和输入框的辅助方法
     */
    private void addLabelAndField(JDialog dialog, String labelText, 
                                JTextField field, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        label.setForeground(themeConfig.textColor);
        dialog.add(label);
        dialog.add(field);
    }
    
    /**
     * 显示预售管理对话框
     */
    private void showPresaleDialog() {
        JDialog dialog = new JDialog(frame, "预售管理", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        Font font = new Font("微软雅黑", Font.BOLD, 32);
        
        // 可售房屋列表
        String[] columnNames = {"编号", "楼栋", "单元", "房间号", "面积", "单价", "状态"};
        Object[][] data = houses.values().stream()
            .filter(h -> "可售".equals(h.getStatus()))
            .map(h -> new Object[]{h.getId(), h.getBuilding(), h.getUnit(), 
                                 h.getRoom(), h.getArea(), h.getPrice(), h.getStatus()})
            .toArray(Object[][]::new);
        
        JTable table = new JTable(data, columnNames);
        table.setFont(font);
        table.setRowHeight(50);
        table.setBackground(themeConfig.bgColor);
        table.setForeground(themeConfig.textColor);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setBackground(themeConfig.themeColor);
        table.getTableHeader().setForeground(Color.WHITE);
        
        JButton sellButton = new JButton("预订房屋");
        JButton myHousesButton = new JButton("我的房产");
        sellButton.setFont(font);
        myHousesButton.setFont(font);
        
        sellButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0) {
                String houseId = (String)table.getValueAt(selectedRow, 0);
                House house = houses.get(houseId);
                house.sell(currentUser);
                
                // 添加到用户的房产列表中
                userHouses.computeIfAbsent(currentUser, k -> new ArrayList<>()).add(house);
                
                JOptionPane.showMessageDialog(dialog, "预订成功!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "请先选择要预订的房屋!");
            }
        });
        
        myHousesButton.addActionListener(e -> {
            showMyHousesDialog();
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(sellButton);
        buttonPanel.add(myHousesButton);
        
        dialog.add(buttonPanel, BorderLayout.NORTH);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        
        dialog.setSize(1600, 1000);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    private void showMyHousesDialog() {
        JDialog dialog = new JDialog(frame, "我的房产", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        Font font = new Font("微软雅黑", Font.BOLD, 32);
        
        String[] columnNames = {"编号", "楼栋", "单元", "房间号", "面积", "单价", "购买日期"};
        List<House> myHouses = userHouses.getOrDefault(currentUser, new ArrayList<>());
        
        Object[][] data = myHouses.stream()
            .map(h -> new Object[]{h.getId(), h.getBuilding(), h.getUnit(), 
                                 h.getRoom(), h.getArea(), h.getPrice(), h.getSaleDate()})
            .toArray(Object[][]::new);
        JTable table = new JTable(data, columnNames);
        table.setFont(font);
        table.setRowHeight(50);
        table.setBackground(themeConfig.bgColor);
        table.setForeground(themeConfig.textColor);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setBackground(themeConfig.themeColor);
        table.getTableHeader().setForeground(Color.WHITE);

        // 添加按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(themeConfig.bgColor);
        
        JButton viewButton = createGradientButton("查看详情");
        viewButton.setFont(font);
        buttonPanel.add(viewButton);
        
        viewButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0) {
                String houseId = (String)table.getValueAt(selectedRow, 0);
                House house = houses.get(houseId);
                
                JDialog detailDialog = new JDialog(dialog, "房产详情", true);
                detailDialog.setLayout(new GridLayout(8, 2, 10, 10));
                detailDialog.getContentPane().setBackground(themeConfig.bgColor);
                
                // 添加详细信息标签
                addDetailLabel(detailDialog, "编号:", house.getId(), font);
                addDetailLabel(detailDialog, "楼栋:", house.getBuilding(), font);
                addDetailLabel(detailDialog, "单元:", house.getUnit(), font);
                addDetailLabel(detailDialog, "房间号:", house.getRoom(), font);
                addDetailLabel(detailDialog, "面积:", String.valueOf(house.getArea()), font);
                addDetailLabel(detailDialog, "单价:", String.valueOf(house.getPrice()), font);
                addDetailLabel(detailDialog, "购买日期:", house.getSaleDate().toString(), font);
                
                detailDialog.setSize(800, 600);
                detailDialog.setLocationRelativeTo(dialog);
                detailDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(dialog, "请先选择要查看的房产!");
            }
        });

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        
        dialog.setSize(1600, 1000);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    private void addDetailLabel(JDialog dialog, String labelText, String value, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        label.setForeground(themeConfig.textColor);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(font);
        valueLabel.setForeground(themeConfig.textColor);
        
        dialog.add(label);
        dialog.add(valueLabel);
    }

    private void createMainGUI() {
        frame = new JFrame("房屋预售系统 - 当前用户: " + currentUser);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(1600, 1000);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 50, 30));
        mainPanel.setBackground(themeConfig.bgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 200));
        
        User currentUserObj = users.get(currentUser);
        boolean isAdmin = currentUserObj != null && "管理员".equals(currentUserObj.getRole());
        
        JButton houseInfoButton = createGradientButton("房屋信息管理");
        JButton presaleButton = createGradientButton("预售管理");
        JButton userButton = createGradientButton("用户管理");
        JButton queryButton = createGradientButton("信息查询");
        JButton statsButton = createGradientButton("统计报表");
        JButton exitButton = createGradientButton("退出系统");
        Font buttonFont = new Font("微软雅黑", Font.BOLD, 32);
        
        houseInfoButton.setFont(buttonFont);
        presaleButton.setFont(buttonFont);
        userButton.setFont(buttonFont);
        queryButton.setFont(buttonFont);
        statsButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);
        
        // 根据用户角色显示不同的功能按钮
        if(isAdmin) {
            mainPanel.add(houseInfoButton);
            mainPanel.add(presaleButton);
            mainPanel.add(userButton);
            mainPanel.add(queryButton);
            mainPanel.add(statsButton);
            mainPanel.add(exitButton);
            
            houseInfoButton.addActionListener(e -> showHouseManageDialog());
            presaleButton.addActionListener(e -> showPresaleDialog());
            userButton.addActionListener(e -> showUserManageDialog());
            queryButton.addActionListener(e -> showQueryDialog());
            statsButton.addActionListener(e -> showStatsDialog());
        } else {
            mainPanel.add(presaleButton);
            mainPanel.add(queryButton);
            mainPanel.add(exitButton);
            
            presaleButton.addActionListener(e -> showPresaleDialog());
            queryButton.addActionListener(e -> showQueryDialog());
        }
        
        exitButton.addActionListener(e -> {
            frame.dispose();
            showLoginDialog();
        });
        
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void showQueryDialog() {
        JDialog dialog = new JDialog(frame, "信息查询", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(themeConfig.bgColor);
        
        Font font = new Font("微软雅黑", Font.BOLD, 32);
        
        // 搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(themeConfig.bgColor);
        JTextField searchField = new JTextField(20);
        JButton searchButton = createGradientButton("搜索");
        searchField.setFont(font);
        searchButton.setFont(font);
        JLabel searchLabel = new JLabel("房屋编号:");
        searchLabel.setFont(font);
        searchLabel.setForeground(themeConfig.textColor);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // 结果表格
        String[] columnNames = {"编号", "楼栋", "单元", "房间号", "面积", "单价", "状态", "购买者", "销售日期"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(font);
        table.setRowHeight(50);
        table.setBackground(themeConfig.bgColor);
        table.setForeground(themeConfig.textColor);
        table.getTableHeader().setFont(font);
        table.getTableHeader().setBackground(themeConfig.themeColor);
        table.getTableHeader().setForeground(Color.WHITE);
        
        searchButton.addActionListener(e -> {
            String searchId = searchField.getText().trim();
            House house = houses.get(searchId);
            model.setRowCount(0);
            if(house != null) {
                model.addRow(new Object[]{
                    house.getId(), house.getBuilding(), house.getUnit(), house.getRoom(),
                    house.getArea(), house.getPrice(), house.getStatus(),
                    house.getBuyer(), house.getSaleDate()
                });
            } else {
                JOptionPane.showMessageDialog(dialog, "未找到该房屋!");
            }
        });
        
        dialog.add(searchPanel, BorderLayout.NORTH);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        
        dialog.setSize(1600, 1000);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HouseSellingSystem();
        });
    }
}
