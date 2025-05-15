import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class LoginPage extends JFrame {
    private static ArrayList<Mahasiswa> daftarMahasiswa = new ArrayList<>();
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JList<String> dataList;
    private DefaultListModel<String> listModel;

    public LoginPage() {
        daftarMahasiswa.add(new Mahasiswa("Janu", "Sintang"));
        daftarMahasiswa.add(new Mahasiswa("Janu", "Sintang"));

        showLoginPage();
    }

    private void showLoginPage() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Menggunakan BorderLayout

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS)); // Tata letak vertikal
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40)); // Padding

        JLabel titleLabel = new JLabel("Login Page", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Jarak

        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        loginPanel.add(usernamePanel);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        loginPanel.add(passwordPanel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Jarak

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> handleLogin());
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin")) {
            showAdminDashboard();
        } else if (username.equals("mahasiswa") && password.equals("mahasiswa")) {
            showMahasiswaDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Salah masukan yang benar!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMahasiswaDashboard() {
        JFrame mahasiswaFrame = new JFrame("Dashboard Mahasiswa");
        mahasiswaFrame.setSize(400, 300);
        mahasiswaFrame.setLocationRelativeTo(null);
        mahasiswaFrame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Dashboard Mahasiswa", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mahasiswaFrame.add(titleLabel, BorderLayout.NORTH);


        listModel = new DefaultListModel<>();
        for (Mahasiswa m : daftarMahasiswa) {
            listModel.addElement(m.toString());
        }
        dataList = new JList<>(listModel);
        dataList.setFont(new Font("Arial", Font.PLAIN, 14));
        mahasiswaFrame.add(new JScrollPane(dataList), BorderLayout.CENTER);


        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            mahasiswaFrame.dispose();
            new LoginPage(); // Kembali ke halaman login
        });
        mahasiswaFrame.add(logoutButton, BorderLayout.SOUTH);

        mahasiswaFrame.setVisible(true);
        dispose();
    }

    private void showAdminDashboard() {
        JFrame adminFrame = new JFrame("Dashboard Admin");
        adminFrame.setSize(500, 400);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Dashboard Admin", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        adminFrame.add(titleLabel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        for (Mahasiswa m : daftarMahasiswa) {
            listModel.addElement(m.toString());
        }
        dataList = new JList<>(listModel);
        dataList.setFont(new Font("Arial", Font.PLAIN, 14));
        adminFrame.add(new JScrollPane(dataList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10)); // Ditambah 1 untuk tombol logout

        JButton tambahButton = new JButton("Tambah Data");
        tambahButton.addActionListener(e -> tambahData());
        buttonPanel.add(tambahButton);

        JButton ubahButton = new JButton("Ubah Data");
        ubahButton.addActionListener(e -> ubahData());
        buttonPanel.add(ubahButton);

        JButton hapusButton = new JButton("Hapus Data");
        hapusButton.addActionListener(e -> hapusData());
        buttonPanel.add(hapusButton);

        JButton keluarButton = new JButton("Keluar");
        keluarButton.addActionListener(e -> {
            adminFrame.dispose();
            System.exit(0);
        });
        buttonPanel.add(keluarButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            adminFrame.dispose();
            new LoginPage();
        });
        buttonPanel.add(logoutButton);

        adminFrame.add(buttonPanel, BorderLayout.EAST);

        adminFrame.setVisible(true);
        dispose();
    }

    private void tambahData() {
        JTextField namaField = new JTextField(15);
        JTextField alamatField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nama:"));
        panel.add(namaField);
        panel.add(new JLabel("Alamat:"));
        panel.add(alamatField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Tambah Data Mahasiswa", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nama = namaField.getText();
            String alamat = alamatField.getText();
            if (!nama.isEmpty() && !alamat.isEmpty()) {
                daftarMahasiswa.add(new Mahasiswa(nama, alamat));
                listModel.addElement("Nama: " + nama + ", Alamat: " + alamat);
            } else {
                JOptionPane.showMessageDialog(null, "Nama dan Alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ubahData() {
        int selectedIndex = dataList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan diubah!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Mahasiswa m = daftarMahasiswa.get(selectedIndex);
        JTextField namaField = new JTextField(m.getNama(), 15);
        JTextField alamatField = new JTextField(m.getAlamat(), 15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nama:"));
        panel.add(namaField);
        panel.add(new JLabel("Alamat:"));
        panel.add(alamatField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Ubah Data Mahasiswa", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nama = namaField.getText();
            String alamat = alamatField.getText();
            if (!nama.isEmpty() && !alamat.isEmpty()) {
                m.setNama(nama);
                m.setAlamat(alamat);
                listModel.set(selectedIndex, "Nama: " + nama + ", Alamat: " + alamat);
            } else {
                JOptionPane.showMessageDialog(null, "Nama dan Alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hapusData() {
        int selectedIndex = dataList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            daftarMahasiswa.remove(selectedIndex);
            listModel.remove(selectedIndex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}