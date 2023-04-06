package com.example.kotlinprac.paging.prac

const val GITHUB_API_HEADER_PREFIX = "Authorization"

// 모든 레포 가져오기
const val GET_ALL_REPOS_URL = "user/repos"
// 레포 검색
const val SEARCH_REPO_URL = "search/repositories?sort=stars"
const val QUERY = "q"
const val PAGE = "page"
const val PER_PAGE = "per_page"

const val NETWORK_PAGE_SIZE = 50    // 한 번에 50개 씩 서버에 데이터 요청
const val STARTING_PAGE_INDEX = 1
const val IN_QUALIFIER = "in:name,description"  // 검색어 뒤에 붙이는 검색 조건