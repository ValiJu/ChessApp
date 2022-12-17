package com.example.chess;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;


class ChessApplicationTests {

	@Test
	@SneakyThrows
	void contextLoads() {
//    ChessPiece c = new Pawn(new Position(1,2), Color.BLACK);
//		System.out.println(c.getClass().getSimpleName());
//		c.getAllPossibleMoves();
//		ArrayList<Position> p = new ArrayList<>();
//		System.out.println(p);
//		p.add(null);
//		System.out.println(p);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(()->task1());
        waitUntilThreadActivityIsDone(future);
		if(future.isDone()){
			System.out.println(future.get());
		}

//		CompletableFuture<String> completableFuture
//				= CompletableFuture.supplyAsync(() -> task1());
//		System.out.println(completableFuture.get());

	}
	@SneakyThrows
	public String task1 (){
		Thread.sleep(5000);
		return "da";
	}
@SneakyThrows
	private void waitUntilThreadActivityIsDone(Future<?> taskExecutor) {
			taskExecutor.get(6000, TimeUnit.MILLISECONDS);

	}

}
