import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MusicPlayerGUI  extends JFrame{


    public static final Color FRAME_COLOR = new Color(205,190,177);
    public static final Color TEXT_COLOR = Color.darkGray;

    private MusicPlayer musicPlayer;

    private JFileChooser jFileChooser;

    private JLabel songTitle, songArtist;
    private JPanel playbackBtns;
    private JButton prevButton, playButton, pauseButton, nextButton;

    public MusicPlayerGUI(){
        super("Music Player");

        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        getContentPane().setBackground(FRAME_COLOR);

        musicPlayer = new MusicPlayer();
        jFileChooser = new JFileChooser();

        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        addGuiComponents();
    }

    private void addGuiComponents(){
        addToolbar();

        //load record image
        JLabel songImage = new JLabel(loadImage("src/assets/recordd.png"));
        songImage.setBounds(0,50, getWidth() - 20, 225);
        add(songImage);

        songTitle = new JLabel("Song Title");
        songTitle.setBounds(0, 285, getWidth() - 10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        //song artist
        songArtist = new JLabel("Artist");
        songArtist.setBounds(0, 315, getWidth() - 10, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 24));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);

        JSlider playbackSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        playbackSlider.setBounds(getWidth()/2 - 300/2, 365, 300, 40);
        playbackSlider.setBackground(null);
        add(playbackSlider);

        addPlaybackBtns();
    }
    private void addToolbar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0,0, getWidth(), 20);

        toolBar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);
        menuBar.setBackground(Color.DARK_GRAY);

        JMenu songMenu= new JMenu("Song");
        menuBar.add(songMenu);
        songMenu.setForeground(Color.white);

        JMenuItem loadSong = new JMenuItem("Load Song");
        songMenu.add(loadSong);
        loadSong.setBackground(Color.DARK_GRAY);
        loadSong.setForeground(Color.white);
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if(selectedFile != null){
                    Song song = new Song(selectedFile.getPath());

                    musicPlayer.loadSong(song);

                    updateSongTitleAndArtist(song);

                    enablePlayButtonDisablePauseButton();
                }
            }
        });

        JMenu playlistMenu = new JMenu("Playlist");
        menuBar.add(playlistMenu);
        playlistMenu.setForeground(Color.WHITE);

        JMenuItem createPlaylist = new JMenuItem("Create Playlist");
        playlistMenu.add(createPlaylist);
        createPlaylist.setBackground(Color.DARK_GRAY);
        createPlaylist.setForeground(Color.WHITE);

        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        playlistMenu.add(loadPlaylist);
        loadPlaylist.setBackground(Color.DARK_GRAY);
        loadPlaylist.setForeground(Color.WHITE);

        add(toolBar);
    }

    private void addPlaybackBtns(){
        playbackBtns = new JPanel();
        playbackBtns.setBounds(0, 435, getWidth() - 10, 80);
        playbackBtns.setBackground(null);

        //previous
        prevButton = new JButton(loadImage("src/assets/previous.png"));
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        playbackBtns.add(prevButton);

        //play
        playButton = new JButton(loadImage("src/assets/play.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playbackBtns.add(playButton);

        //pause
        pauseButton = new JButton(loadImage("src/assets/pause.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        playbackBtns.add(pauseButton);

        //next
        nextButton = new JButton(loadImage("src/assets/next.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        playbackBtns.add(nextButton);

        add(playbackBtns);
    }

    private void updateSongTitleAndArtist(Song song){
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    private void enablePlayButtonDisablePauseButton(){

        playButton.setVisible(false);
        playButton.setEnabled(true);

        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }



    private ImageIcon loadImage(String path){
        try {
            // Read image file from the provided path
            BufferedImage image = ImageIO.read(new File(path));
            // Returns image icon so component can render image
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // If image not found
        return null;
    }
}
