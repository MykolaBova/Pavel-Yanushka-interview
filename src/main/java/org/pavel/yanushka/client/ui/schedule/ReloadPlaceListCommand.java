package org.pavel.yanushka.client.ui.schedule;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import org.pavel.yanushka.client.ui.MainPanel;
import org.pavel.yanushka.common.model.Candidate;

import java.util.List;

public class ReloadPlaceListCommand implements ScheduledCommand {

    private final List<Candidate> candidatesList;

    private final MainPanel mainPanel;

    private int index;

    public ReloadPlaceListCommand(List<Candidate> list, MainPanel mainPanel) {
        candidatesList = list;
        this.mainPanel = mainPanel;
        index = 0;
    }

    @Override
    public void execute() {
        if (index < candidatesList.size()) {
            Candidate candidate = candidatesList.get(index);
            mainPanel.addPlaceToPanel(candidate);
            Scheduler.get().scheduleDeferred(this);
            index++;
        }
    }

}
