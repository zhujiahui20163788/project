
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class mainWindow extends JFrame implements ActionListener{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JFrame bankerAlgoritJFrame;//������
    JLabel[] l;      //������Դ�����������������Ľ���������Դ���󣬱�ǩ
    JTextField[] t;  //��Ӧ�ڱ�ǩ���ı���
    JButton[] b;     //��Ӧȥ��ǩ�İ�ť
    JPanel[] p;      
    int m=0;//��ӵĽ�����
    int n=0;//��ӵ���Դ������
    int[] available;
    int[][] max;
    int[][] allocation;
    int[][] need;
    int[] request;
    String requestP;
    String[] processName;
    String[] processName_Safety;//����һ����ȫ�ƽ�����
    String[] resourceName;
    JPanel[] l1;
    JTextField[] t1;
    public mainWindow(String name){
        super(name);
        setSize(320, 250);
        setLocation(500, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(null);

        l = new JLabel[3];
        t = new JTextField[2];
        b = new JButton[3];
        p = new JPanel[3];

        p[0] = new JPanel();
        p[1] = new JPanel();
        p[2] = new JPanel();

        p[0].setBounds(5, 20, 300, 30);
        p[1].setBounds(5, 60, 300, 30);
        p[2].setBounds(5, 100, 300, 30);

        l[0] = new JLabel("������Դ����������");
        l[1] = new JLabel("���������Ľ�������");
        l[2] = new JLabel("��Դ����");

        t[0] = new JTextField(3);
        t[1] = new JTextField(3);

        b[0] = new JButton("�����Դ");
        b[0].addActionListener(this);
        b[1] = new JButton("��������");
        b[1].addActionListener(this);
        b[2] = new JButton("������Դ");
        b[2].addActionListener(this);

        p[0].add(l[0]);
        p[0].add(t[0]);
        p[0].add(b[0]);

        p[1].add(l[1]);
        p[1].add(t[1]);
        p[1].add(b[1]);

        p[2].add(l[2]);
        p[2].add(b[2]);

        container.add(p[0]);
        container.add(p[1]);
        container.add(p[2]);


        setVisible(true);
    }

/*
 * ���������Դ������
 * */
class AddResourceWindow extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    JLabel ResourceNameL = new JLabel("��Դ��  ");
    JLabel AvailableL = new JLabel("  available"); 
    JButton addResourceOK = new JButton("ȷ��");
    JLabel[] resourceNameJLabel = new JLabel[n];
    JTextField[] AvailableTextField = new JTextField[n];
    JPanel[] addResourceJPanels = new JPanel[n+2]; 

    public AddResourceWindow(){
        super("�����Դ");
        setSize(320, 400);
        setLocation(500, 150);
        Container container = getContentPane();
        container.setLayout(null);

        addResourceJPanels[n+1] = new JPanel();
        addResourceJPanels[n+1].setBounds(5, 20, 300, 30);
        addResourceJPanels[n+1].add(ResourceNameL);
        addResourceJPanels[n+1].add(AvailableL);
        container.add(addResourceJPanels[n+1]);

        /*
         * ��̬��������
         * */
        int y = 60;
        char a = 'A';
        for(int i=0; i<n; i++, y=y+40){
            addResourceJPanels[i] = new JPanel();
            resourceNameJLabel[i] = new JLabel(String.valueOf(a)+":");
            resourceName[i] = String.valueOf(a);
            a = (char)(a+1);
            AvailableTextField[i] = new JTextField(4);
            AvailableTextField[i].setHorizontalAlignment(JTextField.RIGHT);
            addResourceJPanels[i].setBounds(5, y, 300, 30);
            addResourceJPanels[i].add(resourceNameJLabel[i]);
            addResourceJPanels[i].add(AvailableTextField[i]);
            container.add(addResourceJPanels[i]);
        }
        addResourceJPanels[n] = new JPanel();
        addResourceJPanels[n].setBounds(5, y, 300, 30);
        addResourceJPanels[n].add(addResourceOK);
        addResourceOK.addActionListener(this);
        container.add(addResourceJPanels[n]);

        setVisible(true);
    }

    /*
     * ���������Դ������������available����,��������Դ��Ĭ��ΪA��B��C����������
     * */
    void getResource(){
        for(int i=0; i<n; i++){
            try {
                available[i] = Integer.parseInt(AvailableTextField[i].getText());
            } catch (Exception e) {
                // TODO: handle exception
                JOptionPane.showMessageDialog(null,"availableΪ������");
                return;
            }
        }

//      for(int i=0; i<n; i++)
//          System.out.println(resourceName[i]);
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object ob = e.getSource();
        if(ob == addResourceOK){
            getResource();
        }
    }
}

/*
 * �����������̵Ĵ����࣬���������������max�����allocation����
 * */
class StartProcess extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;

    JLabel resourceNameJLabel = new JLabel("       ������");
    JLabel maxJLabel = new JLabel(" max ");
    JLabel allocationJLabel = new JLabel("allocation   ");
    JButton startProcessOK = new JButton("ȷ��");
    JPanel[] startProcessJPanel = new JPanel[m+2];
    JLabel[] processNameJLabel = new JLabel[m];
    JTextField[][] maxJTextField = new JTextField[m][n];
    JTextField[][] allocationJTextField = new JTextField[m][n];

    public StartProcess(){
        super("��������");
        setSize(320, 400);
        setLocation(500, 150);
        Container container = getContentPane();
        container.setLayout(null);

        /*
         *��ӱ�����
         * */
        startProcessJPanel[m+1] = new JPanel();
        startProcessJPanel[m+1].setLayout(new GridLayout(1,3));
        startProcessJPanel[m+1].setBounds(5, 20, 300, 30);
        startProcessJPanel[m+1].add(resourceNameJLabel);
        startProcessJPanel[m+1].add(maxJLabel);
        startProcessJPanel[m+1].add(allocationJLabel);
        container.add(startProcessJPanel[m+1]);

        /*
         * ��ӱ��textfield�飩������Ĭ��Ϊp1��p2��p3��������
         * */
        int y=60;
        for(int i=0; i<m; i++, y=y+40){
            startProcessJPanel[i] = new JPanel();
            processNameJLabel[i] = new JLabel("p"+i+":");
            startProcessJPanel[i].setBounds(5, y, 300, 30);
            processName[i] = "p"+i;
            startProcessJPanel[i].add(processNameJLabel[i]);
            for(int j=0; j<n; j++){
                maxJTextField[i][j] = new JTextField(2);
                maxJTextField[i][j].setHorizontalAlignment(JTextField.RIGHT);
                startProcessJPanel[i].add(maxJTextField[i][j]);
            }
            startProcessJPanel[i].add(new JLabel("*"));
            for(int j=0; j<n; j++){
                allocationJTextField[i][j] = new JTextField(2);
                allocationJTextField[i][j].setHorizontalAlignment(JTextField.RIGHT);
                startProcessJPanel[i].add(allocationJTextField[i][j]);
            }
            container.add(startProcessJPanel[i]);
        }

        /*
         * ���ȷ����ť
         * */
        startProcessJPanel[m] = new JPanel();
        startProcessJPanel[m].setBounds(5, y, 300, 30);
        startProcessJPanel[m].add(startProcessOK);
        startProcessOK.addActionListener(this);
        container.add(startProcessJPanel[m]);


        setVisible(true);
    }

    /*
     * ��textfield�л�ȡ������Ϣ
     * */
    void getProcessMassage(){
        try{
            for(int i=0; i<m; i++){
                for(int j=0; j<n; j++){
                    max[i][j] = Integer.parseInt(maxJTextField[i][j].getText());
                    allocation[i][j] = Integer.parseInt(allocationJTextField[i][j].getText());
                    need[i][j] = max[i][j] - allocation[i][j];
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "������Ϣ����Ϊ������");
            return;
        }
        if(isSafety()==false){
            JOptionPane.showMessageDialog(null, "����Ľ�����Ϣʹ���̴��ڲ���ȫ״̬!");
            return;
        }
//      for(int i=0; i<m; i++){
//          for(int j=0; j<n; j++){
//              System.out.print(max[i][j]);
//          }
//          System.out.println();
//      }
        this.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object ob = e.getSource();
        if(ob == startProcessOK){
            getProcessMassage();
        }
    }

}

/*
 * ������Դ������
 * */
class RequestResourceWindow extends JFrame implements ActionListener{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JLabel rProcessNameL = new JLabel("��������");
    JButton reqResOK = new JButton("ȷ��");
    JButton reqResClose = new JButton("�ر�");
    JTextField rProcessNameT = new JTextField(4);
    JLabel[] resNameL = new JLabel[n];
    JTextField[] resNameT = new JTextField[n];
    JPanel[] resJPanel = new JPanel[n+2];
    Container container = getContentPane();
    public RequestResourceWindow(){
        super("������Դ");
        setSize(320, 400);
        setLocation(500, 150);
        Container container = getContentPane();
        container.setLayout(null);

        resJPanel[n] = new JPanel();
        resJPanel[n].setBounds(5, 20, 300, 30);
        resJPanel[n].add(rProcessNameL);
        resJPanel[n].add(rProcessNameT);
        container.add(resJPanel[n]);

        int y = 60;
        char a = 'A';
        for(int i=0; i<n; i++, y=y+40){
            resJPanel[i] = new JPanel();
            resNameL[i] = new JLabel(String.valueOf(a)+":");
            resourceName[i] = String.valueOf(a);
            a = (char)(a+1);
            resNameT[i] = new JTextField(2);
            resNameT[i].setHorizontalAlignment(JTextField.RIGHT);
            resJPanel[i].setBounds(5, y, 300, 30);
            resJPanel[i].add(resNameL[i]);
            resJPanel[i].add(resNameT[i]);
            container.add(resJPanel[i]);
        }
        resJPanel[n+1] = new JPanel();
        resJPanel[n+1].setBounds(5, y, 300, 30);
        resJPanel[n+1].add(reqResOK);
        resJPanel[n+1].add(reqResClose);
        reqResOK.addActionListener(this);
        reqResClose.addActionListener(this);
        container.add(resJPanel[n+1]);

        setVisible(true);
    }

    /*
     * ��ȡ������Դ����Ϣ,������������Դ���Ƿ��ڰ�ȫ״̬
     * */
    private void getRequestRes(){
        try{
            for(int i=0; i<n; i++){
                request[i] = Integer.parseInt(resNameT[i].getText());

            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "����������Դ������Ϊ������");
            return;
        }
        requestP = rProcessNameT.getText();

        /*
         * �ж�����������Ƿ�Ϸ�����ɺ����㷨�ĵ�һ�ڶ���
         * */
        if(P_id(requestP)==-1){ 
            JOptionPane.showMessageDialog(null, "����Ľ��̲�����");
            return;
        }else if(R_N(P_id(requestP))==false){
            JOptionPane.showMessageDialog(null, "��������Ҫ����Դ�����ѳ����������������");
            return;
        }else if(R_A()==false){
            JOptionPane.showMessageDialog(null, "û���㹻����Դ������ý���");
            return;
        }
        String[] s = new String[2];
        s[0] = null;
        s[1] = "";

        if(bankerAlgorithm()==true){
            s[0] = "��ʱϵͳ���ڰ�ȫ״̬��һ����ȫ�ƽ�����Ϊ��\n";
            for(int i=0; i<m; i++){
                s[1] = s[1] + processName_Safety[i] + "  ";
            }
            JOptionPane.showMessageDialog(null, s[0]+s[1]);
            return;
        }else{
            JOptionPane.showMessageDialog(null, "��ʱϵͳ���ڲ���ȫ״̬��");
            return;
        }

//      
//      System.out.println(P_id(requestP.trim()));
//      System.out.println(requestP);
//      

//      for(int i=0; i<n; i++){
//          System.out.print(request[i]+" ");
//      }
//      System.out.println(requestP);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        Object ob = arg0.getSource();
        if(ob == reqResOK){
            getRequestRes();
        }else if(ob == reqResClose){
            this.dispose();
        }
    }

}

    /*
     * �����Դ��ť���ܣ���ʼ����Դ�������������Ի����ʼ����Դavailable
     * */
    void addResource(){
        try{
            n = Integer.parseInt(t[0].getText());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "��Դ��Ϊ������");
            return;
        }
        //System.out.println(n);
        available = new int[n];
        for(int i=0; i<n; i++){
            available[i] = 0;
        }
        resourceName = new String[n];

        new AddResourceWindow();
//      for(int i=0; i<n; i++){
//          System.out.print(resourceName[i]);
//      }
//      System.out.println();
    }

    /*
     * �ж���Դ�Ƿ����
     * */
    private boolean haveResouce(){
        for(int i=0;i<n;i++){
            if(available[i]!=0){
                return true;
            }
        }
        return false;
    }

    /*
     * �������̣���ӽ���max������Ϣ�������̷���allocation
     * */
    void startProcess(){
        if(haveResouce()==false || n==0){
            JOptionPane.showMessageDialog(null, "���������Դ:��Դ��ȫΪ���û��������Դ��");
        }else{
            try{
                m = Integer.parseInt(t[1].getText());
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "������Ϊ������");
                return;
            }
        //  System.out.print(m);
            processName = new String[m];
            max = new int[m][n];
            allocation = new int[m][n];
            need = new int[m][n];
//          for(int i=0; i<m; i++)
//              for(int j=0; j<n; j++)
//                  System.out.print(max[i][j]);
            new StartProcess();//��������
        }
    }

    /*
     * ��������Դ�Ի��򣬲��Ҽ�����Ƿ���ڰ�ȫ״̬
     * */
    void requestResource(){
            request = new int[n];
            new RequestResourceWindow();
    }

    /*
     * �����㷨����
     * */
    //������
    public boolean bankerAlgorithm(){
        //ͨ������Դ�������֤�����Է�����Դ
        int i = P_id(requestP);
        for(int j=0; j<n; j++){
            available[j] = available[j] - request[j];
            allocation[i][j] += request[j];
            need[i][j] -= request[j];
        }
        if(isSafety()==false){
            for(int j=0; j<n; j++){
                available[j] = available[j] + request[j];
                allocation[i][j] -=request[j];
                need[i][j] -= request[j];
            }
            return false;
        }else 
            return true;
    }

    /*
     * ���ݽ��������ؽ���ID
     * */
    int P_id(String PName){
        for(int i=0; i<m; i++){
            if(processName[i].compareToIgnoreCase(PName) == 0) return i;
        }
        return -1;
    }

    /*
     * �ж�������Դ��request��������Դ��need֮��Ĺ�ϵ,�������request[j]<=need[i][j]����true
     * */
    boolean R_N(int i){
        for(int j=0; j<n; j++){
            if(request[j]>need[i][j]) return false;
        }
        return true;
    }
    /*
     * �ж�������Դ��request�������Դ��available֮��Ĺ�ϵ���������request[j]<=available[j]����ture
     * */
    boolean R_A(){
        for(int j=0; j<n; j++){
            if(request[j]>available[j])return false;
        }
        return true;
    }

    /*
     * �ж�need[i][j]��work[j]�Ĵ�С��С�ڷ���true
     * */
    boolean N_W(int i, int[] work){
        for(int j=0; j<n; j++){
            if(need[i][j]>work[j]) return false;
        }
        return true;
    }
    /*
     * �ҵ�����need[i][j]<=work[j]��finish[i]==false�Ľ��̷��ؽ���id��δ�ҵ�����-1
     * */
    int isNextP(int i, boolean[] finish, int[] work){
        for(int j=i; j<m; j++){
            if(finish[j]==false && N_W(j, work)==true)
                return j;
        }
        for(int k=0; k<i; k++){
            if(finish[k]==false && N_W(k, work)==true)
                return k;
        }
        return -1;
    }
    /*
     * �жϸ���finish��ֵ�ж��Ƿ���ڰ�ȫ״̬��ȫΪtrue���ڰ�ȫ״̬
     * */
    boolean allTrue(boolean[] finish){
        for(int i=0; i<m; i++){
            if(finish[i]==false)return false;
        }
        return true;
    }

    /*
     * �ж��Ƿ�ȫ
     * */
    boolean isSafety(){
        int[] work = new int[n];
        boolean[] finish = new boolean[m];
        processName_Safety = new String[m];
        for(int j=0; j<n; j++){
            work[j] = available[j];
        }
        for(int j=0; j<m; j++){
            finish[j] = false;
        }
        int i=0;
        for(int k=0; k<m; k++){
            i = isNextP(i,finish,work);
            if(i>=0){
                for(int j=0; j<n; j++){
                    work[j] += allocation[i][j];
                }
                finish[i] = true;
                processName_Safety[k] = processName[i];
//              System.out.println(i);
            }else
                break;
        }
        if(allTrue(finish)==true)
            return true;
        else
            return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new mainWindow("���м��㷨ʵ��");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object ob = e.getSource();
        if(ob == b[0]){
            addResource();
        }else if(ob == b[1]){
            startProcess();
        }else if(ob == b[2]){
            requestResource();
        }
    }

}