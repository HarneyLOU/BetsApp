package models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

	@Entity
	@Table(name = "BET")
	public class Bet {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "BetId")
		private int betId;
		
		@ManyToOne
	    @JoinColumn(name = "GameId")
		private Game game;
		
		@Column(name = "Type")
		private int type;
		
		@Column(name = "Amount")
		private double amount;
		
		@Column(name = "BetTime")
		private LocalDateTime betTime;
		
		@Column(name = "State")
		private int state;
		
		public LocalDateTime getBetTime() {
			return betTime;
		}

		public void setBetTime(LocalDateTime betTime) {
			this.betTime = betTime;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public int getBetId() {
			return betId;
		}

		public void setBetId(int betId) {
			this.betId = betId;
		}

		public Game getGame() {
			return game;
		}

		public void setGame(Game game) {
			this.game = game;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public LocalDateTime getDate() {
			return betTime;
		}

		public void setDate(LocalDateTime date) {
			this.betTime = date;
		}

		public Bet() {
			
		}

}
