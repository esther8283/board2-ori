package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.dto.RestBoardDto;
import board.mapper.RestBoardMapper;

@Service
public class RestBoardServiceImpl implements RestBoardService {
	
	@Autowired
	private RestBoardMapper restboardMapper;
	
	@Override
	public List<RestBoardDto> selectRestBoardList() throws Exception {
		return restboardMapper.selectRestBoardList();
	}
	
	@Override
	public RestBoardDto selectRestBoardDetail(int boardIdx) throws Exception {
		restboardMapper.updateRestHitCount(boardIdx);
		return restboardMapper.selectRestBoardDetail(boardIdx);
	}
	
	@Override
	public void updateRestHitCount(int boardIdx) throws Exception {
		restboardMapper.updateRestHitCount(boardIdx);
	}
	
	@Override
	public void insertRestBoard(RestBoardDto data) throws Exception {
		restboardMapper.insertRestBoard(data);
	}
	
	@Override
	public void updateRestBoard(RestBoardDto data) throws Exception {
		restboardMapper.updateRestBoard(data);
	}
	
	@Override
	public void deleteRestBoard(int boardIdx) throws Exception {
		restboardMapper.deleteRestBoard(boardIdx);
	}

}

