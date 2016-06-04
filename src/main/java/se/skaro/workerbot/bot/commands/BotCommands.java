package se.skaro.workerbot.bot.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.skaro.workerbot.data.entity.TextCommand;
import se.skaro.workerbot.data.repository.TextCommandRepository;

@Component
public class BotCommands {

	@Autowired
	private TextCommandRepository textCommandRepository;

	private List<AbstractCommand> commands;
	
	@Autowired
	private CardCommand cardCommand;
	
	@Autowired
	private DecksCommand decksCommand;
	
	@Autowired
	private EquipCommand equipCommand;
	
	@Autowired
	private GemCommand gemCommand;
	
	@Autowired
	private HelpCommand helpCommand;
	
	@Autowired
	private IGNCommand ignCommand;
	
	@Autowired
	private ImageCommand imageCommand;
	
	@Autowired
	private JoinCommand joinCommand;
	
	@Autowired
	private KeywordCommand keywordCommand;
	
	@Autowired
	private LeaveCommand leaveCommand;
	
	@Autowired
	private LegendCommand legendCommand;
	
	@Autowired
	private PriceCommand priceCommand;
	
	@Autowired
	private RatioCommand ratioCommand;
	
	@Autowired
	private TwitchNameCommand twitchNameCommand;
	
	@Autowired
	private WhisperSettingsCommand whisperSettingsCommand;

	public void setup() {
		commands = new ArrayList<>();
		List<TextCommand> textCommands = textCommandRepository.findAll();
		textCommands.forEach(c -> {
			commands.add(new GenericTextCommand(c.getSyntax(), c.getMessage(), c.getHelpMessage()));

			//TODO: Remove sysout, change to log
			System.out.println(c.getSyntax() + " command added");
		});
		
		commands.add(cardCommand);
		commands.add(decksCommand);
		commands.add(equipCommand);
		commands.add(gemCommand);
		commands.add(helpCommand);
		commands.add(ignCommand);
		commands.add(imageCommand);
		commands.add(joinCommand);
		commands.add(keywordCommand);
		commands.add(leaveCommand);
		commands.add(legendCommand);
		commands.add(priceCommand);
		commands.add(ratioCommand);
		commands.add(twitchNameCommand);
		commands.add(whisperSettingsCommand);

	}

	public List<AbstractCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<AbstractCommand> commands) {
		this.commands = commands;
	}
}
