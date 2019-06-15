////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//�ļ��� : fishnet.cpp
//������ : Zhujiahui
//�������� : 2019.03.05
//�޸����� : 2019.03.06
//�������� : Visual Studio 2013
//���ļ�����fishnet�������Ͷ���
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#include<iostream>
#include<fstream>
#include<stdlib.h>
using namespace std;

class Fishnet {
public:
	Fishnet();
	int fileread_write();		//���ļ��ж�����Ϣ���ú�����������
	int input();			//���������Ϣ
	int date_judge();		//����������ڽ��д������
private:
	int m_symbol;			//��Ϣ��ȡ��ʽ��� 
	int m_totalday;			//�洢������
	int m_year;			//�洢���
	int m_mouth;			//�洢�·�
	int m_day;			//�洢��
	int m_daynum[12];		//�洢ÿ�µ����������У����°�28���
};

//fishnet�๹�캯�����������ݵĳ�ʼ��
Fishnet::Fishnet()
{
	m_symbol = 0;
	m_totalday = 0;
	m_year = 0;
	m_mouth = 0;
	m_day = 0;
	m_daynum[0] = 31;
	m_daynum[1] = 28;
	m_daynum[2] = 31;
	m_daynum[3] = 30;
	m_daynum[4] = 31;
	m_daynum[5] = 30;
	m_daynum[6] = 31;
	m_daynum[7] = 31;
	m_daynum[8] = 30;
	m_daynum[9] = 31;
	m_daynum[10] = 30;
	m_daynum[11] = 31;
}

//���ļ��ж�����Ϣ���ú�����������
int Fishnet::fileread_write()
{
	//�����ļ����������󣬲���in.txt�ļ���ȡ��Ϣ
	ifstream infile("in.txt");
	
	//�����ļ���������󣬲���out.txt�ļ������Ϣ
	ofstream outfile("out.txt");
	
	//����ѭ����ֱ����in.txt�ļ�֮�е��������ݶ���
	while (infile)
	{
		//��in.txt�������
		infile >> m_year;
		//��in.txt�����·�
		infile >> m_mouth;
		//��in.txt������
		infile >> m_day;
		//�ļ����һ��������0��β
		//���������β��ǣ�����ֹ����
		if (m_year == 0 || m_mouth == 0 || m_day == 0)
		{
			exit(0);
		}
		//�����ڴ����ķ���ֵ����m_symbol
		m_symbol = date_judge();
		//�жϴ����ĺ�������ֵ�Ƿ�������������������ǣ�����������out.txt�ļ�
		if (m_symbol == 1 || m_symbol == 2 || m_symbol == 3)
		{
			outfile << m_year << "." << m_mouth << "." << m_day << " is fishing" << endl;
		}
		//�жϴ����ĺ�������ֵ�Ƿ�����ɹ��������������ǣ�����������out.txt�ļ�
		else if (m_symbol == 0 || m_symbol == 4)
		{
			outfile << m_year << "." << m_mouth << "." << m_day << " is dying net" << endl;
		}
		//�жϴ����ĺ�������ֵ�Ƿ�Ϊ���ڴ�������������ǣ�����������out.txt�ļ�
		else if (m_symbol == 11)
		{
			outfile << m_year << "." << m_mouth << "." << m_day << " ERROR!!!" << endl;
		}
	}
	//�ر�����ļ�
	outfile.close();
	return 0;
}

//���������Ϣ���ж������ĸ�����
int Fishnet::input()
{
	cout << "Please choose the way of input:" << endl;
	cout << "1 is read from files" << endl;
	cout << "2 is read from cmd" << endl;
	cin >> m_symbol;
	//���������жϻ�����ݵķ�ʽ
	//�������1����ļ��ж�������
	if (m_symbol == 1)
	{
		fileread_write();
	}
	//�������2��������н����������
	else if (m_symbol == 2)
	{
		cout << "Please input the date(the date after 2010,1,1,before 9999,12,31)" << endl;
		//�������н���ֱ������ݣ��·ݣ���
		cout << "Please input the year:";
		cin >> m_year;
		cout << "Please input the mouth:";
		cin >> m_mouth;
		cout << "Please input the day:";
		cin >> m_day;
		//�Դ������н����������ڽ��д�����������
		date_judge();
	}
	//�����������ֲ���1Ҳ����2�����������ʾ������ֹ����
	else
	{
		cout << "your input is out of range" << endl;
		exit(0);
	}
	return 0;
}

//����������ڽ��д������
int Fishnet::date_judge()
{
	//�����������ڵķ�Χ�ж�
	//�����������С��2010�������������ʾ
	//��Ϊ���������룬����ֹ���������ǣ��򷵻ش�����ֵ11
	if (m_year < 2010 || m_year >9999)
	{
		cout << m_year << "." << m_mouth << "." << m_day << " is out of range" << endl;
		if (m_symbol == 2)
		{
			exit(0);
		}
		return 11;
	}
	//���������·ݲ���12����֮�ڻ���С��1�������������ʾ
	//��Ϊ���������룬����ֹ���������ǣ��򷵻ش�����ֵ11
	else if (m_mouth > 12 || m_mouth < 1)
	{
		cout << m_year << "." << m_mouth << "." << m_day << " is out of range" << endl;
		if (m_symbol == 2)
		{
			exit(0);
		}
		return 11;
	}
	//������������������12������Ӧ�·ݵ���������Ϊ�����������������ʾ
	//��Ϊ���������룬����ֹ���������ǣ��򷵻ش�����ֵ11
	else if (m_day < 0 || m_day > m_daynum[m_mouth - 1])
	{
		cout << m_year << "." << m_mouth << "." << m_day << " is out of range" << endl;
		if (m_symbol == 2)
		{
			exit(0);
		}
		return 11;
	}
	for (int i = 2010; i < m_year; i++)
	{
		//�жϵ�ǰ����ǲ�������
		if ((m_year % 4 == 0 && m_year % 100 != 0) || m_year % 400 == 0)
		{
			//�����ǰ��������꣬����������366��
			m_totalday = m_totalday + 366;
		}
		else
		{
			//�����ǰ�����ƽ�꣬����������365��
			m_totalday = m_totalday + 365;
		}
	}
	for (int i = 1; i < m_mouth; i++)
	{
		//�Ե�ǰ�·��е������������
		m_totalday = m_totalday + m_daynum[i - 1];
		//�жϵ�ǰ����ǲ�������,��ǰ�·��Ƿ�Ϊ����
		if (m_mouth == 2 && ((m_year % 4 == 0 && m_year % 100 != 0) || m_year % 400 == 0))
		{
			//�����ǰ��������꣬�ҵ�ǰ�·�Ϊ���£������Ϊ29�죬��������һ��
			m_totalday = m_totalday + 1;
		}
	}
	//�������е�����m_day����������ӣ���õ���2010.01.01����������
	m_totalday = m_totalday + m_day;
	//��m_symbol���г�ʼ������ֹ��������
	m_symbol = 0;
	//���м��㣬��������m_totalday����5����
	m_symbol = m_totalday % 5;
	//��m_totalday���г�ʼ�����������ļ�������´�ʹ��
	m_totalday = 0;
	//���������ж��Ǵ��㻹����
	if (m_symbol == 1 || m_symbol == 2 || m_symbol == 3)
	{
		//�����ڴ��㣬�������ʾ
		cout << m_year << "." << m_mouth << "." << m_day << " is fishing" << endl;
		return m_symbol;
	}
	else if (m_symbol == 0 || m_symbol == 4)
	{
		//������ɹ�����������ʾ
		cout << m_year << "." << m_mouth << "." << m_day << " is drying net" << endl;
		return m_symbol;
	}
}

int main()
{
	Fishnet FN;
	FN.input();
	return 0;
}