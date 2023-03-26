// CEBFileEngine.cpp: implementation of the CCEBFileEngine class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "CEBFileEngine.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
CCEBFileEngine::CCEBFileEngine(LPCTSTR pszFileName)
{
	if (!m_cebFile.Open(pszFileName, CFile::modeRead | CFile::shareDenyWrite))
		return;
	// CreateFileMapping
	m_hMapFile = CreateFileMapping((HANDLE)(m_cebFile.m_hFile),0,PAGE_WRITECOPY,0,0,NULL);
	if (m_hMapFile == NULL ||m_hMapFile == INVALID_HANDLE_VALUE)
	{
		return ;
	}
	
	m_pbFile = (LPBYTE)MapViewOfFile(m_hMapFile,FILE_MAP_COPY,0,0,0);
	if (m_pbFile == NULL)
	{
		CloseHandle(m_hMapFile);
		return ;
	}


	m_dwOriginFileSize = (DWORD)m_cebFile.GetLength();
	

	memcpy(&m_cfh,m_pbFile,sizeof(CEBFILEHEADER));
	char szCopyright[20];
	memcpy(szCopyright, m_cfh.szHeaderInfo, 11);
	szCopyright[11] = '\0';
	if (strcmp(szCopyright, CEB_FILEHEADER_COPYRIGHT) != 0)
	{
		ASSERT(FALSE);
		return;
	}
	m_strCopyright = szCopyright;

	if (m_cfh.szHeaderInfo[11] == 0 && m_cfh.szHeaderInfo[12] == 0 && m_cfh.szHeaderInfo[13] == 0)
	{
		// 表明这是CEB 3.0或更高的版本。
		m_nMajorVer = m_cfh.szHeaderInfo[CEBHEADER_MAJORVER_OFFSETPOS];
		m_nMinorVer = m_cfh.szHeaderInfo[CEBHEADER_MINORVER_OFFSETPOS];
	}
	else
	{
		// 否则为CEB 3.0以前的版本。
		char szVer[20];
		memcpy(szVer, m_cfh.szHeaderInfo, 16);
		szVer[16] = '\0';

		CString strVer = szVer;
		strVer = strVer.Right(4);
		CString strMajor = strVer.Left(1);
		CString strMinor = strVer.Right(2);
		m_nMajorVer = (BYTE)_ttoi(strMajor);
		m_nMinorVer = (BYTE)_ttoi(strMinor);

		// 这里防止一些老版本的CEB却写着3.0的版本号而导致后续的错误。
		if (m_nMajorVer >= 3)
			m_nMajorVer = 2;
	}

	CEBINDEXITEM item;
	for (int i = 0; i < m_cfh.wIndexCount; i++)
	{
		memcpy(&item,
			m_pbFile + sizeof(CEBFILEHEADER) + i*sizeof(CEBINDEXITEM),
			sizeof(CEBINDEXITEM));
		m_aIndex.Add(item);
	}


	m_strFileName = pszFileName;
}

CCEBFileEngine::~CCEBFileEngine(void)
{

//	Close();
}

void CCEBFileEngine::Close()
{
	m_aIndex.RemoveAll();
	if (m_pbFile)
	{
		UnmapViewOfFile(m_pbFile);

	}
	if (m_hMapFile)
	{
		CloseHandle(m_hMapFile);

	}
	m_cebFile.Close();

}


BOOL CCEBFileEngine::FindIndexItem(BYTE byIndexType, DWORD* pdwComID, CEBINDEXITEM& item)
{
	memset(&item, 0, sizeof(item));

	int nIndex = FindIndexItemIndex(byIndexType, pdwComID);
	if (nIndex != -1)
	{
		item = m_aIndex[nIndex];
		return TRUE;
	}
	else
		return FALSE;
}

int CCEBFileEngine::FindIndexItemIndex(BYTE byIndexType, DWORD* pdwComID)
{
	int nCount = m_aIndex.GetSize();
	if (nCount <= 0)
	{
		ASSERT(FALSE);
		return FALSE;
	}

	// 判读内存是否可读
	if (byIndexType == CEB_INDEXTYPE_PLUGINDATA && !AfxIsValidAddress(pdwComID, 8, FALSE))
	{
		ASSERT(FALSE);
		return FALSE;
	}

	int nIndex = 0;
	BOOL bFound = FALSE;

	for (nIndex = 0; nIndex < nCount; nIndex++)
	{
		if (byIndexType == m_aIndex[nIndex].byIndexType)
		{
			if (byIndexType == CEB_INDEXTYPE_PLUGINDATA)
			{
				if (memcmp(pdwComID, m_aIndex[nIndex].byPlugInID, sizeof(m_aIndex[nIndex].byPlugInID)) == 0)
				{
					bFound = TRUE;
					break;
				}
			}
			else
			{
				bFound = TRUE;
				break;
			}
		}
	}

	if (bFound)
		return nIndex;
	else
		return -1;
}

BOOL CCEBFileEngine::GetBookInfo(CEBBOOKINFOA& bi)
{
	memset(&bi, 0, sizeof(CEBBOOKINFOA));
	
	if (GetIndexCount() <= 0)
		return FALSE;
	
	CEBINDEXITEM itemBookInfo;	
	BOOL bItemExist = FindIndexItem(CEB_INDEXTYPE_BOOKINFO, NULL, itemBookInfo);
	if (!bItemExist)
		return FALSE;
	
	
	if (m_cebFile.GetLength() < itemBookInfo.dwOffsetPos + itemBookInfo.dwDataBlockLength)
		return FALSE;
	
	int nCount = sizeof(CEBBOOKINFOA);
	memcpy(&bi,m_pbFile +itemBookInfo.dwOffsetPos,
		nCount);
	return TRUE;

}


