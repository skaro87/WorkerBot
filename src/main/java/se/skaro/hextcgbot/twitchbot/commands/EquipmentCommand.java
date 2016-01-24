package se.skaro.hextcgbot.twitchbot.commands;

import org.pircbotx.hooks.events.MessageEvent;
import se.skaro.hextcgbot.events.MessageSender;
import se.skaro.hextcgbot.model.Equipment;
import se.skaro.hextcgbot.repository.jpa.JpaRepository;

import java.util.List;

/**
 * Sends back equipments.
 */
//FIXME: Rapid messages, fix this.
public class EquipmentCommand extends AbstractCommand {
    @Override
    public void call(String commandSyntax, MessageEvent event) {
        String equipmentName = fixWhiteSpacesAndSymbols(getMessageWithoutCommand(commandSyntax, event));
        if (equipmentName.length() > 3) {
            List<Equipment> equipments = JpaRepository.findEquipmentByAffectedCardName(equipmentName);
            if (equipments.isEmpty()) {
                MessageSender.sendMessage(event, "No equipment found for card " + equipmentName);
                return;
            }
            if (equipments.size() == 1) {
                MessageSender.sendMessage(event, equipments.get(0).toString());
                return;
            }
            if (equipments.size() == 2 && equipments.get(0).getAffectedCardName().equals(equipments.get(1).getAffectedCardName())) {
                MessageSender.sendMessage(event, equipments.get(0).toString());
                MessageSender.sendMessage(event, equipments.get(1).toString());
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Found equipments for multiple cards: ");
            equipments.stream().filter(e -> !sb.toString().contains(e.getAffectedCardName()))
                    .forEach(e -> sb.append("[").append(e.getAffectedCardName()).append("] "));
            MessageSender.sendMessage(event, sb.toString());
        } else {
            MessageSender.sendMessage(event, "You need at least 4 characters to do a search");
        }
    }
}
