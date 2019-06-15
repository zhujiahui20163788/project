////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//文件名 : fishnet.cpp
//创建者 : Zhujiahui
//创建日期 : 2019.03.05
//修改日期 : 2019.03.06
//开发环境 : Visual Studio 2013
//本文件是类fishnet的声明和定义
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
	int fileread_write();		//从文件中读入信息调用函数处理后输出
	int input();			//获得输入信息
	int date_judge();		//对输入的日期进行处理并输出
private:
	int m_symbol;			//信息获取方式标记 
	int m_totalday;			//存储总天数
	int m_year;			//存储年份
	int m_mouth;			//存储月份
	int m_day;			//存储日
	int m_daynum[12];		//存储每月的日数，其中，二月按28天记
};

//fishnet类构造函数，进行数据的初始化
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

//从文件中读入信息调用函数处理后输出
int Fishnet::fileread_write()
{
	//定义文件输入流对象，并打开in.txt文件读取信息
	ifstream infile("in.txt");
	
	//定义文件输出流对象，并打开out.txt文件输出信息
	ofstream outfile("out.txt");
	
	//进行循环，直到将in.txt文件之中的所有数据读完
	while (infile)
	{
		//从in.txt读入年份
		infile >> m_year;
		//从in.txt读入月份
		infile >> m_mouth;
		//从in.txt读入日
		infile >> m_day;
		//文件最后一行以数字0结尾
		//如果遇到结尾标记，则终止程序
		if (m_year == 0 || m_mouth == 0 || m_day == 0)
		{
			exit(0);
		}
		//将日期处理后的返回值赋给m_symbol
		m_symbol = date_judge();
		//判断处理后的函数返回值是否满足打鱼的条件，如果是，则输出结果到out.txt文件
		if (m_symbol == 1 || m_symbol == 2 || m_symbol == 3)
		{
			outfile << m_year << "." << m_mouth << "." << m_day << " is fishing" << endl;
		}
		//判断处理后的函数返回值是否满足晒网的条件，如果是，则输出结果到out.txt文件
		else if (m_symbol == 0 || m_symbol == 4)
		{
			outfile << m_year << "." << m_mouth << "." << m_day << " is dying net" << endl;
		}
		//判断处理后的函数返回值是否为日期错误条件，如果是，则输出结果到out.txt文件
		else if (m_symbol == 11)
		{
			outfile << m_year << "." << m_mouth << "." << m_day << " ERROR!!!" << endl;
		}
	}
	//关闭输出文件
	outfile.close();
	return 0;
}

//获得输入信息并判断链接哪个函数
int Fishnet::input()
{
	cout << "Please choose the way of input:" << endl;
	cout << "1 is read from files" << endl;
	cout << "2 is read from cmd" << endl;
	cin >> m_symbol;
	//依据输入判断获得数据的方式
	//如果输入1则从文件中读入数据
	if (m_symbol == 1)
	{
		fileread_write();
	}
	//如果输入2则从命令行界面读入数据
	else if (m_symbol == 2)
	{
		cout << "Please input the date(the date after 2010,1,1,before 9999,12,31)" << endl;
		//从命令行界面分别读入年份，月份，日
		cout << "Please input the year:";
		cin >> m_year;
		cout << "Please input the mouth:";
		cin >> m_mouth;
		cout << "Please input the day:";
		cin >> m_day;
		//对从命令行界面读入的日期进行处理，并输出结果
		date_judge();
	}
	//如果输入的数字不是1也不是2则输出错误提示，并终止程序
	else
	{
		cout << "your input is out of range" << endl;
		exit(0);
	}
	return 0;
}

//对输入的日期进行处理并输出
int Fishnet::date_judge()
{
	//进行输入日期的范围判定
	//如果输入的年份小于2010，则输出错误提示
	//若为命令行输入，则终止程序，若不是，则返回错误标记值11
	if (m_year < 2010 || m_year >9999)
	{
		cout << m_year << "." << m_mouth << "." << m_day << " is out of range" << endl;
		if (m_symbol == 2)
		{
			exit(0);
		}
		return 11;
	}
	//如果输入的月份不是12个月之内或者小于1，则输出错误提示
	//若为命令行输入，则终止程序，若不是，则返回错误标记值11
	else if (m_mouth > 12 || m_mouth < 1)
	{
		cout << m_year << "." << m_mouth << "." << m_day << " is out of range" << endl;
		if (m_symbol == 2)
		{
			exit(0);
		}
		return 11;
	}
	//如果输入的日数不符合12月中相应月份的天数或者为负数，则输出错误提示
	//若为命令行输入，则终止程序，若不是，则返回错误标记值11
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
		//判断当前年份是不是闰年
		if ((m_year % 4 == 0 && m_year % 100 != 0) || m_year % 400 == 0)
		{
			//如果当前年份是闰年，则总天数加366天
			m_totalday = m_totalday + 366;
		}
		else
		{
			//如果当前年份是平年，则总天数加365天
			m_totalday = m_totalday + 365;
		}
	}
	for (int i = 1; i < m_mouth; i++)
	{
		//对当前月份中的天数进行相加
		m_totalday = m_totalday + m_daynum[i - 1];
		//判断当前年份是不是闰年,当前月份是否为二月
		if (m_mouth == 2 && ((m_year % 4 == 0 && m_year % 100 != 0) || m_year % 400 == 0))
		{
			//如果当前年份是闰年，且当前月份为二月，则二月为29天，总天数加一天
			m_totalday = m_totalday + 1;
		}
	}
	//将日期中的日数m_day和总天数相加，则得到从2010.01.01以来的天数
	m_totalday = m_totalday + m_day;
	//对m_symbol进行初始化，防止产生错误
	m_symbol = 0;
	//进行计算，将总天数m_totalday除以5求余
	m_symbol = m_totalday % 5;
	//对m_totalday进行初始化，以用于文件输入的下次使用
	m_totalday = 0;
	//根据余数判断是打鱼还是网
	if (m_symbol == 1 || m_symbol == 2 || m_symbol == 3)
	{
		//若是在打鱼，则输出提示
		cout << m_year << "." << m_mouth << "." << m_day << " is fishing" << endl;
		return m_symbol;
	}
	else if (m_symbol == 0 || m_symbol == 4)
	{
		//若是在晒网，则输出提示
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