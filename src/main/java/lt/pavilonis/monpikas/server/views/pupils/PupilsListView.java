package lt.pavilonis.monpikas.server.views.pupils;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.ItemClickEvent.ItemClickListener;

import static com.vaadin.ui.Table.Align.CENTER;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import lt.pavilonis.monpikas.server.dto.AdbPupilDto;
import lt.pavilonis.monpikas.server.views.converters.OptionalBooleanCellConverter;
import lt.pavilonis.monpikas.server.views.converters.OptionalCellConverter;
import lt.pavilonis.monpikas.server.views.converters.SimpleStringToLongConverter;

public class PupilsListView extends VerticalLayout {

   private Table t = new Table();
   BeanContainer<Long, AdbPupilDto> container = new BeanContainer<>(AdbPupilDto.class);
   PupilListFilterPanel pupilListFilterPanel = new PupilListFilterPanel();

   public PupilsListView() {
      setSizeFull();
      container.setBeanIdProperty("cardId");
      t.setSizeFull();
      t.setContainerDataSource(container);
      //TODO use setColumnHeaderS
      t.setColumnHeader("cardId", "Kortelės #");
      t.setColumnHeader("firstName", "Vardas");
      t.setColumnHeader("lastName", "Pavarde");
      t.setColumnHeader("birthDate", "Gimimo data");
      t.setColumnHeader("breakfastPortion", "Pusryčiai");
      t.setColumnHeader("dinnerPortion", "Pietus");
      t.setColumnHeader("grade", "Klasė");
      t.setColumnHeader("comment", "Komentaras");
      t.setConverter("comment", new OptionalCellConverter());
      t.setVisibleColumns(
            new String[]{
                  "cardId", "firstName", "lastName", "birthDate", "breakfastPortion", "dinnerPortion", "grade", "comment"
            }
      );
      t.setColumnWidth("breakfastPortion", 85);
      t.setColumnWidth("dinnerPortion", 85);
      t.setColumnWidth("grade", 85);
      t.setColumnWidth("birthDate", 130);
      t.setColumnWidth("cardId", 90);
      t.setConverter("breakfastPortion", new OptionalBooleanCellConverter());
      t.setConverter("dinnerPortion", new OptionalBooleanCellConverter());
      t.setConverter("grade", new OptionalCellConverter());
      t.setConverter("birthDate", new OptionalCellConverter());
      t.setConverter("cardId", new SimpleStringToLongConverter());
      t.setColumnAlignment("breakfastPortion", CENTER);
      t.setColumnAlignment("dinnerPortion", CENTER);
      t.setColumnAlignment("birthDate", CENTER);
      t.setColumnCollapsingAllowed(true);
      t.setColumnCollapsed("cardId", true);
      t.setSelectable(true);
      t.setNullSelectionAllowed(false);
      t.setCacheRate(5);
      addComponents(pupilListFilterPanel, t);
      setExpandRatio(t, 1f);
      pupilListFilterPanel.addFilterButtonListener(filterButtonClicked -> {
               container.removeAllContainerFilters();
               container.addContainerFilter(pupilListFilterPanel.getFilter());
            }
      );
      pupilListFilterPanel.addCancelFilterButtonListener(cancelFilterButtonClicked -> {
         pupilListFilterPanel.cleanFields();
         container.removeAllContainerFilters();
      });
   }

   public void setTableClickListener(ItemClickListener listener) {
      t.addItemClickListener(listener);
   }

   public BeanContainer<Long, AdbPupilDto> getContainer() {
      return container;
   }
}