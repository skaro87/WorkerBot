package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skaro.hextcgbot.model.Equipment;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;
import se.skaro.hextcgbot.twitchbot.excpetions.SearchMessageToShortException;
import se.skaro.hextcgbot.util.MessageSender;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sends back equipments.
 */
//FIXME: Rapid messages, fix this.
@Component
public class EquipmentCommand extends AbstractCommand {

    @Autowired
    private MessageSender messageSender;

    public EquipmentCommand(String syntax, boolean isCommandCaseSensitive, String description) {
        super(syntax, isCommandCaseSensitive, description);
    }

    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String equipmentName = fixWhiteSpaces(getMessageWithoutCommand(commandSyntax, event));
        if (equipmentName.length() < SearchMessageToShortException.DEFAULT_MINIMUM_LENGTH) {
            throw new SearchMessageToShortException();
        }

        List<Equipment> equipments = JpaRepository.findEquipmentByAffectedCardName(equipmentName, true);
        if (equipments.isEmpty()) {
            equipments = JpaRepository.findEquipmentByAffectedCardName(equipmentName, false);
        }

        if (equipments.isEmpty()) {
            messageSender.sendMessage(event, "No equipment found for card " + equipmentName);
            return;
        }
        if (equipments.size() == 1) {
            messageSender.sendMessage(event, equipments.get(0).toString());
            return;
        }
        if (equipments.size() == 2 && equipments.get(0).getAffectedCardName().equals(equipments.get(1).getAffectedCardName())) {
            messageSender.sendMessage(event, equipments.get(0).toString());
            messageSender.sendMessage(event, equipments.get(1).toString());
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Found equipments for multiple cards: ");
        for (String affectedCard : equipments.stream().map(Equipment::getAffectedCardName).collect(Collectors.toSet())) {
            sb.append("[").append(affectedCard).append("] ");
        }
        messageSender.sendMessage(event, sb.toString().trim());
    }
}
