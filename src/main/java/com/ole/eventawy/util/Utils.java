package com.ole.eventawy.util;

import org.modelmapper.ModelMapper;

public class Utils {
	
	
public  static <D> D convertToDto(ModelMapper modelMapper,Object obj,Class<D> c) {
		
		return modelMapper.map(obj,c);
	}
	
	

	public  static <S> S convertToEntity(ModelMapper modelMapper,Object obj,Class<S> c) {
		
		return modelMapper.map(obj,c);
	}

}
