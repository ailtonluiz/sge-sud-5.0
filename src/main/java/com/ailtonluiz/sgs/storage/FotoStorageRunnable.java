package com.ailtonluiz.sgs.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.ailtonluiz.sgs.dto.FotoDTO;
import com.ailtonluiz.sgs.storage.FotoStorage;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<FotoDTO> resultado;
	private FotoStorage fotoStorage;
	
	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage) {
		this.files = files;
		this.resultado = resultado;
		this.fotoStorage = fotoStorage;
	}

	@Override
	public void run() {
		String nomeFoto = this.fotoStorage.salvarTemporariamente(files);
		String contentType = files[0].getContentType();
		resultado.setResult(new FotoDTO(nomeFoto, contentType));
	}

}